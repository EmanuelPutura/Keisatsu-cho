package ubb.keisatsu.cms.controller;

import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ubb.keisatsu.cms.model.dto.*
import ubb.keisatsu.cms.model.entities.*
import ubb.keisatsu.cms.model.entities.Comment
import ubb.keisatsu.cms.model.entities.Conference
import ubb.keisatsu.cms.model.dto.AccountTopicsOfInterestDto
import ubb.keisatsu.cms.model.dto.TopicsDto
import ubb.keisatsu.cms.model.entities.Account
import ubb.keisatsu.cms.model.entities.TopicOfInterest
import ubb.keisatsu.cms.model.entities.UserRole
import ubb.keisatsu.cms.service.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@CrossOrigin
class ReviewerController(private val accountsService: AccountsService, private val topicsOfInterestService: TopicsOfInterestService, private val conferencesService: ConferencesService, private val paperService: PaperService, private val commentsService: CommentService, private val reviewService: ReviewService) {

    private val log = LoggerFactory.logger(ReviewerController::class.java)

    @GetMapping("accounts/topics")
    @PreAuthorize("hasRole('REVIEWER')")
    fun getTopicsOfReviewer(@RequestParam(name="accountID") accountId: Int): TopicsDto {
        val token = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken;
        val account = token.principal as Account
        log.info("GET request for the topics of the reviewer with email=${account.email}")

        val topics: String = topicsOfInterestService.convertTopicsArrayToString(topicsOfInterestService.findAllForAccount(account.id))
        return TopicsDto(topics)
    }

    @PutMapping("accounts/topics")
    @Transactional
    @PreAuthorize("hasRole('REVIEWER')")
    fun updateReviewerTopicsOfInterest(@RequestBody accountTopicsOfInterestDto: AccountTopicsOfInterestDto) {
        val token = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken;
        val account = token.principal as Account
        log.info("PUT request for updating the topics of the reviewer with email=${account.email}")


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
    @PreAuthorize("hasRole('REVIEWER')")
    fun getAllConferences(): MutableSet<ConferenceDto>{
        log.info("GET request for all conferences")
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
    @PreAuthorize("hasRole('REVIEWER')")
    fun getPapersToBid(@RequestParam(name="accountID") accountId: Int, @RequestParam(name="conferenceID") conferenceId: Int, @RequestParam(name="topics") topics: String): MutableSet<PaperDetailsDto>{
        log.info("GET request get paper to bid")
        val result: MutableSet<PaperDetailsDto> = mutableSetOf()
        if(topics.length==0){
            return result
        }
        val listOfTopics = this.topicsOfInterestService.getTopicsArrayFromString(topics)
        this.paperService.retrievePapersFromConference(conferenceId).forEach{ paper ->
            if((paper.reviewer?.id ?: Int) != accountId && paper.decision == PaperDecision.NOT_YET_DECIDED && reviewService.notInConflict(accountId,paper.id) && reviewService.isDecisionNotTaken(paper.id)) {
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
        }
        return result;
    }

    @GetMapping("reviewers/comments")
    @PreAuthorize("hasRole('REVIEWER')")
    fun getComments(@RequestParam(name="paperId") paperId: Int): MutableSet<CommentDto>{
        log.info("GET request get paper")
        val result: MutableSet<CommentDto> = mutableSetOf()
        commentsService.retrieveCommentsForPaper(paperId).forEach{ comment ->  result.add(CommentDto(comment.reviewer.userName, comment.comment))}
        return result
    }

    @PostMapping("reviewers/comment")
    @PreAuthorize("hasRole('REVIEWER')")
    fun addComment(@RequestBody commentDto: CommentSubmitDto): ErrorDto {
        val token = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken;
        val account = token.principal as Account
        log.info("POST request to add comment by ${account.email}")


        val paper = paperService.retrievePaper(commentDto.paperID) ?: return ErrorDto(false, "Invalid paper id")
        commentsService.addComment(Comment(paper ,account,commentDto.comment))
        return ErrorDto(true)
    }

    @GetMapping("papers/to_review")
    @PreAuthorize("hasRole('REVIEWER')")
    fun getPapersToReview(@RequestParam(name="accountID") accountId: Int): MutableSet<PaperFromAuthorDto>{
        val papers: MutableSet<PaperFromAuthorDto> = mutableSetOf<PaperFromAuthorDto>()
        paperService.retrieveAll().forEach{ paper: Paper ->
            if(((paper.reviewer?.id ?: Int) == accountId) && paper.decision == PaperDecision.NOT_YET_DECIDED && reviewService.notReviewed(paper.id,accountId)){
                papers.add(PaperFromAuthorDto(paper.id, paper.title, paper.abstract, accountsService.convertToAccountUserDataDtos(paper.paperAuthors),paper.keywords, paper.topicOfInterest.name, paper.conference.name))
            }
        }
        return papers
    }

    @PostMapping("reviewers/acceptPaper")
    @PreAuthorize("hasRole('REVIEWER')")
    fun acceptPaper(@RequestBody paperSentenceDto: PaperSentenceDto){
        log.info("POST request to accept paper with id=${paperSentenceDto.paperID}")
        reviewService.acceptPaper(paperSentenceDto.token, paperSentenceDto.paperID)
    }

    @PostMapping("reviewers/rejectPaper")
    @PreAuthorize("hasRole('REVIEWER')")
    fun rejectPaper(@RequestBody paperSentenceDto: PaperSentenceDto){
        log.info("POST request to reject paper with id=${paperSentenceDto.paperID}")
        reviewService.rejectPaper(paperSentenceDto.token, paperSentenceDto.paperID)
    }

    @PostMapping("reviewers/conflict")
    @PreAuthorize("hasRole('REVIEWER')")
    fun signalConflict(@RequestBody paperSentenceDto: PaperSentenceDto): ErrorDto{
        val token = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken;
        val account = token.principal as Account
        log.info("POST request to signal a conflict by ${account.email}")

        val paper: Paper = paperService.retrievePaper(paperSentenceDto.paperID)
            ?: return ErrorDto(false, "The paper does not exist")

        val review: Review = reviewService.retrieveReview(paperSentenceDto.token, paperSentenceDto.paperID)
        review.reviewStatus = ReviewStatus.CONFLICT
        reviewService.addReview(review)

        val newReview: Review? = reviewService.getFirstBid(paperSentenceDto.paperID)
        if(newReview != null) {
            newReview.reviewStatus = ReviewStatus.PENDING
            reviewService.addReview(newReview)
            paper.reviewer = newReview.reviewer
            paperService.addPaper(paper)
            return ErrorDto(true)
        }

        var laziestReviewer: Account? = null
        var minNumberOfReviews: Int = 1000
        accountsService.retrieveReviewers().forEach{ reviewer ->
            val pendingReviews = reviewService.getPendingReviewsOfReviewer(reviewer.id)
            if(reviewService.notInConflict(reviewer.id, paper.id) && pendingReviews < minNumberOfReviews){
                minNumberOfReviews = pendingReviews
                laziestReviewer = reviewer
            }
        }

        if(laziestReviewer != null){
            paper.reviewer = laziestReviewer
            reviewService.addReview(Review(laziestReviewer!!, paper))
            paperService.addPaper(paper)
        }


        return ErrorDto(true)
    }

    @PostMapping("reviewers/bid")
    @PreAuthorize("hasRole('REVIEWER')")
    fun bid(@RequestBody paperSentenceDto: PaperSentenceDto): ErrorDto{
        val token = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken;
        val account = token.principal as Account
        log.info("POST request to bid for paper with id=${paperSentenceDto.paperID} by ${account.email}")

        val paper: Paper = paperService.retrievePaper(paperSentenceDto.paperID)
            ?: return ErrorDto(false, "The paper does not exist")

        reviewService.addReview(Review(account,paper,ReviewStatus.BID))
        return ErrorDto(true)
    }
}
