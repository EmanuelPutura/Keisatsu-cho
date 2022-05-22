package ubb.keisatsu.cms.controller;

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ubb.keisatsu.cms.model.dto.*
import ubb.keisatsu.cms.model.entities.Comment
import ubb.keisatsu.cms.model.entities.Conference
import ubb.keisatsu.cms.model.entities.TopicOfInterest
import ubb.keisatsu.cms.model.entities.UserRole
import ubb.keisatsu.cms.service.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@CrossOrigin
class ReviewerController(private val accountsService: AccountsService, private val topicsOfInterestService: TopicsOfInterestService, private val conferencesService: ConferencesService, private val paperService: PaperService, private val commentsService: CommentService) {

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

    @GetMapping("papers/to_bid")
    fun getPapersToBid(@RequestParam(name="accountID") accountId: Int, @RequestParam(name="conferenceID") conferenceId: Int, @RequestParam(name="topics") topics: String): MutableSet<PaperDetailsDto>{
        val result: MutableSet<PaperDetailsDto> = mutableSetOf()
        if(topics.length==0){
            return result
        }
        val listOfTopics = this.topicsOfInterestService.getTopicsArrayFromString(topics)
        this.paperService.retrievePapersFromConference(conferenceId).forEach{ paper ->
            listOfTopics.forEach { topic ->
                if (topic.id==paper.topicOfInterest.id) {
                    result.add(
                        PaperDetailsDto(
                            paper.id,
                            paper.title,
                            paper.abstract,
                            accountsService.convertToAccountUserDataDtos(paper.paperAuthors),
                            paper.keywords,
                            paper.topicOfInterest.name,
                            paper.conference.id
                        )
                    )
                }
            }
        }
        return result;
    }

    @GetMapping("reviewers/comments")
    fun getComments(@RequestParam(name="paperId") paperId: Int): MutableSet<CommentDto>{
        val result: MutableSet<CommentDto> = mutableSetOf()
        commentsService.retrieveCommentsForPaper(paperId).forEach{ comment ->  result.add(CommentDto(comment.reviewer.userName, comment.comment))}
        return result
    }

    @PostMapping("reviewers/comment")
    fun addComment(@RequestBody commentDto: CommentSubmitDto): ErrorDto {
        val account = accountsService.retrieveAccount(commentDto.token) ?: return ErrorDto(false, "Invalid account id!")
        if (account.role != UserRole.REVIEWER) {
            return ErrorDto(false, "Invalid account role: only a reviewer can add comments for a paper!")
        }
        val paper = paperService.retrievePaper(commentDto.paperID) ?: return ErrorDto(false, "Invalid paper id")
        commentsService.addComment(Comment(paper ,account,commentDto.comment))
        return ErrorDto(true)
    }
}
