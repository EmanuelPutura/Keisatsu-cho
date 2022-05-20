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
        private val paperService: PaperService, private val paperEvaluationService: ChairPaperEvaluationService) {

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

    @GetMapping("papers/get")
    fun getPapers(@RequestParam(name="accountID") accountId: Int): MutableSet<PaperDetailsDto>{
        val papersDtoSet: MutableSet<PaperDetailsDto> = mutableSetOf()
        paperService.retrieveAll().forEach{ paper ->
            val topic: String = paper.topicID!!.name
            val conferenceID = paper.conferenceId!!.id

            papersDtoSet.add(PaperDetailsDto(paper.id, paper.title, paper.abstract, accountsService.convertToAccountUserDataDtos(paper.paperAuthors),
                    paper.keywords, topic, conferenceID))
        }
        return papersDtoSet
    }

    @PostMapping("conferences/add")
    fun addConference(@RequestBody conferenceDto: ConferenceSubmitDto) {
        val account = accountsService.retrieveAccount(conferenceDto.email)
        if (account == null || account.role != UserRole.CHAIR) {
            return
        }

        conferencesService.addConference(Conference(conferenceDto.name, conferenceDto.url, conferenceDto.subtitles, account))
    }

    @PostMapping("accounts/conferenceTopics")
    @Transactional
    fun setConferenceTopicsOfInterest(@RequestBody topicOfInterestDto: TopicOfInterestDto) {
        val conference: Conference = conferencesService.retrieveConference(topicOfInterestDto.conferenceID) ?: return
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
    }

    @PutMapping("accounts/conferenceDeadlines")
    @Transactional
    fun updateConferenceTopicsOfInterest(@RequestBody conferenceDeadlinesDto: ConferenceDeadlinesDto) {
        val conference: Conference = conferencesService.retrieveConference(conferenceDeadlinesDto.conferenceID)
                ?: return
        var conferenceDeadlines: ConferenceDeadlines = conference.conferenceDeadlines
                ?: ConferenceDeadlines(conferenceDeadlinesDto.submission, conferenceDeadlinesDto.review, conferenceDeadlinesDto.acceptance, conferenceDeadlinesDto.upload)

        conferenceDeadlines.paperSubmissionDeadline = conferenceDeadlinesDto.submission
        conferenceDeadlines.paperReviewDeadline = conferenceDeadlinesDto.review
        conferenceDeadlines.acceptanceNotificationDeadline = conferenceDeadlinesDto.acceptance
        conferenceDeadlines.acceptedPaperUploadDeadline = conferenceDeadlinesDto.upload

        conferenceDeadlines = conferenceDeadlinesService.addConferenceDeadlines(conferenceDeadlines)
        conference.conferenceDeadlines = conferenceDeadlines
    }

    @PutMapping("accounts/papers")
    fun acceptPaper(@RequestBody paperEvaluationDto: PaperEvaluationDto){
        val paper: Paper = paperService.retrievePaper(paperEvaluationDto.paperID) ?: return
        val account: Account = accountsService.retrieveAccount(paperEvaluationDto.chairID) ?: return
        if ( account.role != UserRole.CHAIR) {
            return
        }

        val paperKey: ChairPaperKey = ChairPaperKey(paperEvaluationDto.paperID,paperEvaluationDto.chairID)
        paperEvaluationService.addEvaluation(ChairPaperEvaluation(paperKey, paper, account, paperEvaluationDto.response))
    }
}
