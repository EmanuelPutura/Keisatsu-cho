package ubb.keisatsu.cms.controller;

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ubb.keisatsu.cms.model.dto.AccountTopicsOfInterestDto
import ubb.keisatsu.cms.model.dto.TopicsDto
import ubb.keisatsu.cms.model.entities.TopicOfInterest
import ubb.keisatsu.cms.model.entities.UserRole
import ubb.keisatsu.cms.service.AccountsService
import ubb.keisatsu.cms.service.TopicsOfInterestService;

@RestController
@CrossOrigin
class ReviewerController(private val accountsService: AccountsService, private val topicsOfInterestService: TopicsOfInterestService) {

    @GetMapping("accounts/topics")
    fun getTopicsOfReviewer(@RequestParam(name="accountID") accountId: Int): TopicsDto {
        val account = accountsService.retrieveAccountByEmail(accountId)
        if (account == null || account.role != UserRole.REVIEWER) {
            return TopicsDto("")
        }
        val topics: String = topicsOfInterestService.convertTopicsArrayToString(topicsOfInterestService.findAllForAccount(accountId))
        return TopicsDto(topics)
    }

    @PutMapping("accounts/topics")
    @Transactional
    fun updateReviewerTopicsOfInterest(@RequestBody accountTopicsOfInterestDto: AccountTopicsOfInterestDto) {
        val account = accountsService.retrieveAccountByEmail(accountTopicsOfInterestDto.token)
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

}
