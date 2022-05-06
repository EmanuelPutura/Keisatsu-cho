package ubb.keisatsu.cms.controller

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ubb.keisatsu.cms.model.dto.ConferenceDetailsDto
import ubb.keisatsu.cms.model.dto.ConferenceSubmitDto
import ubb.keisatsu.cms.model.dto.TopicOfInterestDto
import ubb.keisatsu.cms.model.entities.Conference
import ubb.keisatsu.cms.model.entities.TopicOfInterest
import ubb.keisatsu.cms.model.entities.UserRole
import ubb.keisatsu.cms.service.AccountsService
import ubb.keisatsu.cms.service.ConferencesService
import ubb.keisatsu.cms.service.TopicsOfInterestService

@RestController
@CrossOrigin
class ChairController(private var conferencesService: ConferencesService, private var accountsService: AccountsService,
                      private var topicsOfInterestService: TopicsOfInterestService) {

    @GetMapping("conferences/get")
    fun getConferencesOrganizedBy(@RequestParam(name = "accountID") accountId: Int): MutableSet<ConferenceDetailsDto> {
        val conferenceDtoSet: MutableSet<ConferenceDetailsDto> = mutableSetOf()

        conferencesService.findByMainOrganiser(accountId).forEach { conference ->
            val topics: String = topicsOfInterestService.convertTopicsArrayToString(topicsOfInterestService.findAllForConference(conference.id))
            conferenceDtoSet.add(ConferenceDetailsDto(conference.id, conference.name, conference.url, conference.subtitles, topics,
                conference.conferenceDeadlines?.paperSubmissionDeadline, conference.conferenceDeadlines?.paperReviewDeadline,
                conference.conferenceDeadlines?.acceptanceNotificationDeadline, conference.conferenceDeadlines?.acceptedPaperUploadDeadline
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
}
