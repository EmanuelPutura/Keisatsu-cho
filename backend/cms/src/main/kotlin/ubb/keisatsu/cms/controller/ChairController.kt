package ubb.keisatsu.cms.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ubb.keisatsu.cms.model.dto.*
import ubb.keisatsu.cms.model.entities.*
import ubb.keisatsu.cms.service.*
import java.time.format.DateTimeFormatter

/**
 * Chair controller
 *
 * @property conferencesService
 * @property accountsService
 * @property topicsOfInterestService
 * @property conferenceDeadlinesService
 * @property paperService
 * @constructor Create empty Chair controller
 */
@RestController
@CrossOrigin
class ChairController(
        private val conferencesService: ConferencesService, private val accountsService: AccountsService,
        private val topicsOfInterestService: TopicsOfInterestService, private val conferenceDeadlinesService: ConferenceDeadlinesService,
        private val paperService: PaperService, private val reviewService: ReviewService) {

    /**
     * Get conferences organized by
     *
     * @param accountId
     * @return
     */
    @GetMapping("conferences/get")
    @PreAuthorize("hasRole('CHAIR')")
    fun getConferencesOrganizedBy(@RequestParam(name = "accountID") accountId: Int): MutableSet<ConferenceDto> {
        val conferenceDtoSet: MutableSet<ConferenceDto> = mutableSetOf()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val token = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken;
        val account = token.principal as Account

        conferencesService.findByMainOrganiser(account.id).forEach { conference ->
            val topics: String = topicsOfInterestService.convertTopicsArrayToString(topicsOfInterestService.findAllForConference(conference.id))
            conferenceDtoSet.add(ConferenceDto(conference.id, conference.name, conference.url, conference.subtitles, topics,
                    conference.conferenceDeadlines?.paperSubmissionDeadline?.format(formatter), conference.conferenceDeadlines?.paperReviewDeadline?.format(formatter),
                    conference.conferenceDeadlines?.acceptanceNotificationDeadline?.format(formatter), conference.conferenceDeadlines?.acceptedPaperUploadDeadline?.format(formatter)
            ))
        }

        return conferenceDtoSet
    }

    /**
     * Add conference
     *
     * @param conferenceDto
     * @return
     */
    @PostMapping("conferences/add")
    @PreAuthorize("hasRole('CHAIR')")
    fun addConference(@RequestBody conferenceDto: ConferenceSubmitDto): ErrorDto {
//        val token = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken;
//        val account = token.principal as Account
        val account = accountsService.retrieveAccountByEmail(conferenceDto.email) ?: return ErrorDto(false, "Invalid account email!")
        conferencesService.addConference(Conference(conferenceDto.name, conferenceDto.url, conferenceDto.subtitles, account))
        return ErrorDto(true)
    }

    /**
     * Set conference topics of interest
     *
     * @param topicOfInterestDto
     * @return
     */
    @PostMapping("accounts/conferenceTopics")
    @Transactional
    @PreAuthorize("hasRole('CHAIR')")
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

    /**
     * Update conference topics of interest
     *
     * @param conferenceDeadlinesDto
     * @return
     */
    @PutMapping("accounts/conferenceDeadlines")
    @Transactional
    @PreAuthorize("hasRole('CHAIR')")
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

    /**
     * Get papers
     *
     * @param accountId
     * @return
     */
    @GetMapping("papers/get")
    @PreAuthorize("hasRole('CHAIR')")
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
            if (paper.conference.mainOrganiser.id == accountId && (conferenceDeadlines == null || conferenceDeadlinesService.isDeadlineStillValid(conferenceDeadlines.acceptanceNotificationDeadline))) {
                if(reviewService.isRejected(paper.id)) {
                    papersDtoSet.add(
                        PaperDetailsDto(
                            paper.id,
                            paper.title,
                            paper.abstract,
                            accountsService.convertToAccountUserDataDtos(paper.paperAuthors),
                            paper.keywords,
                            topic,
                            conferenceID
                        )
                    )
                }
            }
        }

        return papersDtoSet
    }

    /**
     * Make decision regarding paper
     *
     * @param paperEvaluationDto
     * @return
     */
    @PutMapping("accounts/papers")
    @PreAuthorize("hasRole('CHAIR')")
    fun makeDecisionRegardingPaper(@RequestBody paperEvaluationDto: PaperEvaluationDto): ErrorDto {
        val paper: Paper = paperService.retrievePaper(paperEvaluationDto.paperID) ?: return ErrorDto(false, "Invalid paper ID!")
        val conference = conferencesService.retrieveConference(paperEvaluationDto.conferenceID) ?: return ErrorDto(false, "Invalid conference ID!")
        val conferenceDeadlines = conference.conferenceDeadlines

        // a paper can be accepted only before the acceptance deadline
        if (conferenceDeadlines != null && !conferenceDeadlinesService.isDeadlineStillValid(conferenceDeadlines.acceptanceNotificationDeadline)) {
            return ErrorDto(false, "Invalid acceptance notification conference deadline!")
        }

        val token = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken;
        val account = token.principal as Account

        paper.decision = if (paperEvaluationDto.response) PaperDecision.ACCEPTED else PaperDecision.REJECTED
        paper.decisionMaker = account
        paperService.addPaper(paper)

        return ErrorDto(true)
    }
}
