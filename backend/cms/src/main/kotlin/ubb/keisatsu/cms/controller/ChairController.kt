package ubb.keisatsu.cms.controller

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ubb.keisatsu.cms.model.dto.*
import ubb.keisatsu.cms.model.entities.*
import ubb.keisatsu.cms.service.*
import java.time.format.DateTimeFormatter

@RestController
@CrossOrigin
class ChairController(
        private val conferencesService: ConferencesService, private val accountsService: AccountsService,
        private val topicsOfInterestService: TopicsOfInterestService, private val conferenceDeadlinesService: ConferenceDeadlinesService,
        private val paperService: PaperService) {

    @GetMapping("conferences/get")
    fun getConferencesOrganizedBy(@RequestParam(name = "accountID") accountId: Int): MutableSet<ConferenceDto> {
        val conferenceDtoSet: MutableSet<ConferenceDto> = mutableSetOf()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        conferencesService.findByMainOrganiser(accountId).forEach { conference ->
            val topics: String = topicsOfInterestService.convertTopicsArrayToString(topicsOfInterestService.findAllForConference(conference.id))
            conferenceDtoSet.add(ConferenceDto(conference.id, conference.name, conference.url, conference.subtitles, topics,
                    conference.conferenceDeadlines?.paperSubmissionDeadline?.format(formatter), conference.conferenceDeadlines?.paperReviewDeadline?.format(formatter),
                    conference.conferenceDeadlines?.acceptanceNotificationDeadline?.format(formatter), conference.conferenceDeadlines?.acceptedPaperUploadDeadline?.format(formatter)
            ))
        }

        return conferenceDtoSet
    }

    @PostMapping("conferences/add")
    fun addConference(@RequestBody conferenceDto: ConferenceSubmitDto): ErrorDto {
        val account = accountsService.retrieveAccountByEmail(conferenceDto.email) ?: return ErrorDto(false, "Invalid account email!")
        if (account.role != UserRole.CHAIR) {
            return ErrorDto(false, "Invalid account role: only a chair can add conferences to the database!")
        }

        conferencesService.addConference(Conference(conferenceDto.name, conferenceDto.url, conferenceDto.subtitles, account))
        return ErrorDto(true)
    }

    @PostMapping("accounts/conferenceTopics")
    @Transactional
    fun setConferenceTopicsOfInterest(@RequestBody topicOfInterestDto: TopicOfInterestDto): ErrorDto {
        val conference: Conference = conferencesService.retrieveConference(topicOfInterestDto.conferenceID) ?: return ErrorDto(false, "Invalid conference ID!")
        conference.topicsOfInterest.clear()

        topicOfInterestDto.topics.lines().forEach { topic ->
            if (topic == "")
                return@forEach

            var topicOfInterest: TopicOfInterest = topicsOfInterestService.retrieveTopicOfInterest(topic)
                    ?: TopicOfInterest(topic)
            topicOfInterest = topicsOfInterestService.addTopicOfInterest(topicOfInterest)

            topicOfInterest.conferencesForTopic.add(conference)
            conference.topicsOfInterest.add(topicOfInterest)
        }

        return ErrorDto(true)
    }

    @PutMapping("accounts/conferenceDeadlines")
    @Transactional
    fun updateConferenceTopicsOfInterest(@RequestBody conferenceDeadlinesDto: ConferenceDeadlinesDto): ErrorDto {
        // conference deadlines validation
        if (!conferenceDeadlinesService.validateDeadlines(conferenceDeadlinesDto.submission, conferenceDeadlinesDto.review,
                conferenceDeadlinesDto.acceptance, conferenceDeadlinesDto.upload)) {
            return ErrorDto(false, "Invalid conference deadlines!")
        }

        val conference: Conference = conferencesService.retrieveConference(conferenceDeadlinesDto.conferenceID)
                ?: return ErrorDto(false, "Invalid conference ID!")
        var conferenceDeadlines: ConferenceDeadlines = conference.conferenceDeadlines
                ?: ConferenceDeadlines(conferenceDeadlinesDto.submission, conferenceDeadlinesDto.review, conferenceDeadlinesDto.acceptance, conferenceDeadlinesDto.upload)

        conferenceDeadlines.paperSubmissionDeadline = conferenceDeadlinesDto.submission
        conferenceDeadlines.paperReviewDeadline = conferenceDeadlinesDto.review
        conferenceDeadlines.acceptanceNotificationDeadline = conferenceDeadlinesDto.acceptance
        conferenceDeadlines.acceptedPaperUploadDeadline = conferenceDeadlinesDto.upload

        conferenceDeadlines = conferenceDeadlinesService.addConferenceDeadlines(conferenceDeadlines)
        conference.conferenceDeadlines = conferenceDeadlines

        return ErrorDto(true)
    }

    @GetMapping("papers/get")
    fun getPapers(@RequestParam(name="accountID") accountId: Int): MutableSet<PaperDetailsDto>{
        val papersDtoSet: MutableSet<PaperDetailsDto> = mutableSetOf()
//        paperService.retrieveAll().
        paperService.retrieveUploadedPapersWithoutCameraReadyCopy().
        forEach{ paper ->
            val topic: String = paper.topicOfInterest.name
            val conferenceID = paper.conference.id
            val conference = conferencesService.retrieveConference(conferenceID) ?: return@forEach
            val conferenceDeadlines = conference.conferenceDeadlines

            // return the current paper only if its acceptance deadline is not set or if it is after the current date
            if (conferenceDeadlines == null || conferenceDeadlinesService.isDeadlineStillValid(conferenceDeadlines.acceptanceNotificationDeadline)) {
                papersDtoSet.add(PaperDetailsDto(paper.id, paper.title, paper.abstract, accountsService.convertToAccountUserDataDtos(paper.paperAuthors),
                    paper.keywords, topic, conferenceID))
            }
        }

        return papersDtoSet
    }

    @PutMapping("accounts/papers")
    fun makeDecisionRegardingPaper(@RequestBody paperEvaluationDto: PaperEvaluationDto): ErrorDto {
        val paper: Paper = paperService.retrievePaper(paperEvaluationDto.paperID) ?: return ErrorDto(false, "Invalid paper ID!")
        val conference = conferencesService.retrieveConference(paperEvaluationDto.conferenceID) ?: return ErrorDto(false, "Invalid conference ID!")
        val conferenceDeadlines = conference.conferenceDeadlines

        // a paper can be accepted only before the acceptance deadline
        if (conferenceDeadlines != null && !conferenceDeadlinesService.isDeadlineStillValid(conferenceDeadlines.acceptanceNotificationDeadline)) {
            return ErrorDto(false, "Invalid acceptance notification conference deadline!")
        }

        val account: Account = accountsService.retrieveAccount(paperEvaluationDto.chairID) ?: return ErrorDto(false, "Invalid account ID!")
        if ( account.role != UserRole.CHAIR) {
            return ErrorDto(false, "Invalid account role!")
        }

        paper.decision = if (paperEvaluationDto.response) PaperDecision.ACCEPTED else PaperDecision.REJECTED
        paper.decisionMaker = account
        paperService.addPaper(paper)

        return ErrorDto(true)
    }
}
