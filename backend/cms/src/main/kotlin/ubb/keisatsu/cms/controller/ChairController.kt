package ubb.keisatsu.cms.controller

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ubb.keisatsu.cms.model.dto.ConferenceDeadlinesDto
import ubb.keisatsu.cms.model.dto.ConferenceDetailsDto
import ubb.keisatsu.cms.model.dto.ConferenceSubmitDto
import ubb.keisatsu.cms.model.dto.TopicOfInterestDto
import ubb.keisatsu.cms.model.entities.Conference
import ubb.keisatsu.cms.model.entities.ConferenceDeadlines
import ubb.keisatsu.cms.model.entities.TopicOfInterest
import ubb.keisatsu.cms.model.entities.UserRole
import ubb.keisatsu.cms.service.AccountsService
import ubb.keisatsu.cms.service.ConferenceDeadlinesService
import ubb.keisatsu.cms.service.ConferencesService
import ubb.keisatsu.cms.service.TopicsOfInterestService
import java.time.format.DateTimeFormatter

@RestController
@CrossOrigin
class ChairController(private val conferencesService: ConferencesService, private val accountsService: AccountsService,
                      private val topicsOfInterestService: TopicsOfInterestService, private val conferenceDeadlinesService: ConferenceDeadlinesService) {

    @GetMapping("conferences/get")
    fun getConferencesOrganizedBy(@RequestParam(name = "accountID") accountId: Int): MutableSet<ConferenceDetailsDto> {
        val conferenceDtoSet: MutableSet<ConferenceDetailsDto> = mutableSetOf()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        conferencesService.findByMainOrganiser(accountId).forEach { conference ->
            val topics: String = topicsOfInterestService.convertTopicsArrayToString(topicsOfInterestService.findAllForConference(conference.id))
            conferenceDtoSet.add(ConferenceDetailsDto(conference.id, conference.name, conference.url, conference.subtitles, topics,
                conference.conferenceDeadlines?.paperSubmissionDeadline?.format(formatter), conference.conferenceDeadlines?.paperReviewDeadline?.format(formatter),
                conference.conferenceDeadlines?.acceptanceNotificationDeadline?.format(formatter), conference.conferenceDeadlines?.acceptedPaperUploadDeadline?.format(formatter)
            ))
        }

        return conferenceDtoSet
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

            var topicOfInterest: TopicOfInterest = topicsOfInterestService.retrieveTopicOfInterest(topic) ?: TopicOfInterest(topic)
            topicOfInterest = topicsOfInterestService.addTopicOfInterest(topicOfInterest)

            topicOfInterest.conferencesForTopic.add(conference)
            conference.topicsOfInterest.add(topicOfInterest)
        }
    }

    @PutMapping("accounts/conferenceDeadlines")
    @Transactional
    fun updateConferenceTopicsOfInterest(@RequestBody conferenceDeadlinesDto: ConferenceDeadlinesDto) {
        val conference: Conference = conferencesService.retrieveConference(conferenceDeadlinesDto.conferenceID) ?: return
        var conferenceDeadlines: ConferenceDeadlines = conference.conferenceDeadlines ?: ConferenceDeadlines(conferenceDeadlinesDto.submission, conferenceDeadlinesDto.review, conferenceDeadlinesDto.acceptance, conferenceDeadlinesDto.upload)

        conferenceDeadlines.paperSubmissionDeadline = conferenceDeadlinesDto.submission
        conferenceDeadlines.paperReviewDeadline = conferenceDeadlinesDto.review
        conferenceDeadlines.acceptanceNotificationDeadline = conferenceDeadlinesDto.acceptance
        conferenceDeadlines.acceptedPaperUploadDeadline = conferenceDeadlinesDto.upload

        conferenceDeadlines = conferenceDeadlinesService.addConferenceDeadlines(conferenceDeadlines)
        conference.conferenceDeadlines = conferenceDeadlines
    }
}
