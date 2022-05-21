package ubb.keisatsu.cms.controller;

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ubb.keisatsu.cms.model.dto.AccountTopicsOfInterestDto
import ubb.keisatsu.cms.model.dto.ConferenceDto
import ubb.keisatsu.cms.model.dto.TopicsDto
import ubb.keisatsu.cms.model.entities.TopicOfInterest
import ubb.keisatsu.cms.model.entities.UserRole
import ubb.keisatsu.cms.service.AccountsService
import ubb.keisatsu.cms.service.ConferencesService
import ubb.keisatsu.cms.service.TopicsOfInterestService;
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@CrossOrigin
class ReviewerController(private val accountsService: AccountsService, private val topicsOfInterestService: TopicsOfInterestService, private val conferencesService: ConferencesService) {

    @GetMapping("accounts/topics")
    fun getTopicsOfReviewer(@RequestParam(name="accountID") accountId: Int): TopicsDto {
        val account = accountsService.retrieveAccount(accountId)
        if (account == null || account.role != UserRole.REVIEWER) {
            return TopicsDto("")
        }
        val topics: String = topicsOfInterestService.convertTopicsArrayToString(topicsOfInterestService.findAllForAccount(accountId))
        return TopicsDto(topics)
    }

    @PutMapping("accounts/topics")
    @Transactional
    fun updateReviewerTopicsOfInterest(@RequestBody accountTopicsOfInterestDto: AccountTopicsOfInterestDto) {
        val account = accountsService.retrieveAccount(accountTopicsOfInterestDto.token)
        if (account == null || account.role != UserRole.REVIEWER) {
            return
        }
        account.topicsOfInterest.clear()
        accountTopicsOfInterestDto.topics.lines().forEach { topic ->
            if (topic == "")
                return@forEach

            var topicOfInterest: TopicOfInterest = topicsOfInterestService.retrieveTopicOfInterest(topic) ?: TopicOfInterest(topic)
            topicOfInterest = topicsOfInterestService.addTopicOfInterest(topicOfInterest)

            topicOfInterest.accountsForTopic.add(account)
            account.topicsOfInterest.add(topicOfInterest)
        }
    }

    @GetMapping("conferences/getAll")
    fun getAllConferences(): MutableSet<ConferenceDto>{
        val conferenceDtoSet: MutableSet<ConferenceDto> = mutableSetOf()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        conferencesService.retrieveAll().forEach { conference ->
            println("hei")
            if(conference.conferenceDeadlines?.paperReviewDeadline?.isBefore(LocalDate.now()) == false) {
                val topics: String = topicsOfInterestService.convertTopicsArrayToString(
                    topicsOfInterestService.findAllForConference(conference.id)
                )
                conferenceDtoSet.add(
                    ConferenceDto(
                        conference.id,
                        conference.name,
                        conference.url,
                        conference.subtitles,
                        topics,
                        conference.conferenceDeadlines?.paperSubmissionDeadline?.format(formatter),
                        conference.conferenceDeadlines?.paperReviewDeadline?.format(formatter),
                        conference.conferenceDeadlines?.acceptanceNotificationDeadline?.format(formatter),
                        conference.conferenceDeadlines?.acceptedPaperUploadDeadline?.format(formatter)
                    )
                )
            }
        }

        return conferenceDtoSet
    }

}
