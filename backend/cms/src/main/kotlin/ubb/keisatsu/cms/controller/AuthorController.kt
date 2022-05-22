package ubb.keisatsu.cms.controller

import org.springframework.core.io.InputStreamResource
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ubb.keisatsu.cms.model.dto.*
import ubb.keisatsu.cms.model.entities.*
import ubb.keisatsu.cms.service.*
import java.io.File
import java.io.FileInputStream
import java.time.format.DateTimeFormatter

@RestController
@CrossOrigin
class AuthorController(private val conferencesService: ConferencesService, private val topicsOfInterestService: TopicsOfInterestService,
                       private val accountsService: AccountsService, private val paperService: PaperService, private val fileUploadService: FileUploadService,
                       private val conferenceDeadlinesService: ConferenceDeadlinesService, private val reviewService: ReviewService) {

    private val ACCEPTED_PAPER_REQUEST_TYPE: String = "accepted"
    private val MISSING_FULL_PAPER_REQUEST_TYPE: String = "missingFull"

    @GetMapping("conferences/all")
    @PreAuthorize("hasRole('AUTHOR')")
    fun getAllConferences(): MutableSet<ConferenceDto> {
        val conferenceDtoSet: MutableSet<ConferenceDto> = mutableSetOf()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        conferencesService.retrieveAll().forEach{ conference ->
            val topics: String = topicsOfInterestService.convertTopicsArrayToString(topicsOfInterestService.findAllForConference(conference.id))
            val conferenceDeadlines = conference.conferenceDeadlines

            // only the conferences whose paper submission deadline is still valid should be returned
            if (conferenceDeadlines == null || conferenceDeadlinesService.isDeadlineStillValid(conferenceDeadlines.paperSubmissionDeadline)) {
                conferenceDtoSet.add(
                    ConferenceDto(conference.id, conference.name, conference.url, conference.subtitles, topics,
                        conference.conferenceDeadlines?.paperSubmissionDeadline?.format(formatter), conference.conferenceDeadlines?.paperReviewDeadline?.format(formatter),
                        conference.conferenceDeadlines?.acceptanceNotificationDeadline?.format(formatter), conference.conferenceDeadlines?.acceptedPaperUploadDeadline?.format(formatter))
                )
            }
        }

        return conferenceDtoSet
    }

    @GetMapping("/papers")
    @PreAuthorize("hasRole('AUTHOR')")
    fun getPapers(@RequestParam token: Int, @RequestParam type: String): Collection<PaperFromAuthorDto> {
        val token = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken;
        val account = token.principal as Account

        if (ACCEPTED_PAPER_REQUEST_TYPE == type) {
            val checkDeadlinesFunction = { deadlines: ConferenceDeadlines? -> deadlines == null || conferenceDeadlinesService.isDeadlineStillValid(deadlines.paperSubmissionDeadline) }
            return getRequestedPapers(account, paperService::retrievePapersHavingAuthorWithoutCameraReadyCopy, checkDeadlinesFunction)
        }
        else if (MISSING_FULL_PAPER_REQUEST_TYPE == type) {
            val checkDeadlinesFunction = { deadlines: ConferenceDeadlines? -> deadlines == null || conferenceDeadlinesService.isDeadlineStillValid(deadlines.acceptedPaperUploadDeadline) }
            return getRequestedPapers(account, paperService::retrieveNotUploadedPapersHavingAuthor, checkDeadlinesFunction)
        }

        return mutableSetOf()  // return an empty set
    }

    @GetMapping("/papers/getFullPaper")
    @PreAuthorize("hasRole('CHAIR') or hasRole('REVIEWER')")
//    @PreAuthorize("hasRole('AUTHOR')")
    fun getFullPaperFile(@RequestParam paperId: Int): InputStreamResource? {
        val paper = paperService.retrievePaper(paperId) ?: return null
        val fullPaperPath = paper.fullPaper ?: return null
        return InputStreamResource(FileInputStream(File(fullPaperPath)))
    }

    @GetMapping("/papers/getCameraReadyCopy")
    @PreAuthorize("hasRole('CHAIR') or hasRole('REVIEWER')")
//    @PreAuthorize("hasRole('AUTHOR')")
    fun getPaperCameraReadyCopy(@RequestParam paperId: Int): InputStreamResource? {
        val paper = paperService.retrievePaper(paperId) ?: return null
        val cameraReadyCopyPath = paper.cameraReadyCopy ?: return null
        return try { InputStreamResource(FileInputStream(File(cameraReadyCopyPath))) } catch (e: Exception) { null }
    }

    @PostMapping("/papers")
    @PreAuthorize("hasRole('AUTHOR')")
    fun submitPaper(@RequestBody submittedPaperDetailsDto: SubmittedPaperDetailsDto): ErrorDto {
        val conference = conferencesService.retrieveConference(submittedPaperDetailsDto.conference) ?: return ErrorDto(false, "Invalid conference ID!")
        val topicOfInterest = topicsOfInterestService.retrieveTopicOfInterest(submittedPaperDetailsDto.interestTopic) ?: return ErrorDto(false, "Invalid topic of interest ID!")

        var paper = Paper(submittedPaperDetailsDto.title, submittedPaperDetailsDto.keywords, topicOfInterest, submittedPaperDetailsDto.abstract, conference)
        val authorsSet: MutableSet<Account> = mutableSetOf()
        var foundPaperSubmittingAuthor = false

        // validation for the authors list
        submittedPaperDetailsDto.authors.forEach{ authorDto ->
            val author = accountsService.retrieveAccountByEmail(authorDto.email) ?: return ErrorDto(false, "Invalid author email: '${authorDto.email}'!")
            if (author.role != UserRole.AUTHOR || author.address != authorDto.address) {
                return ErrorDto(false, "Invalid data for author with email '${authorDto.email}")
            }

            if (author.id == submittedPaperDetailsDto.token) {
                foundPaperSubmittingAuthor = true
            }

            authorsSet.add(author)
        }

        if (!foundPaperSubmittingAuthor) {
            return ErrorDto(false, "Author who submitted the paper should be present in the authors list!")
        }

        // add authors to paper only if all the authors are valid
        authorsSet.forEach{ author ->
            paper.paperAuthors.add(author)
        }

        var laziestReviewer: Account? = null
        var minNumberOfReviews: Int = 1000
        accountsService.retrieveReviewers().forEach{ reviewer ->
            var pendingReviews = reviewService.getPendingReviewsOfReviewer(reviewer.id)
            if(pendingReviews < minNumberOfReviews){
                minNumberOfReviews = pendingReviews
                laziestReviewer = reviewer
            }
        }
        paper = paperService.addPaper(paper)
        if(laziestReviewer != null){
            paper.reviewer = laziestReviewer
            reviewService.addReview(Review(laziestReviewer!!, paper))
        }
        return ErrorDto(true)
    }

    @PutMapping("/papers/uploadPaper")
    @PreAuthorize("hasRole('AUTHOR')")
    fun uploadFullPaper(@ModelAttribute uploadFullPaperDto: UploadFullPaperDto): ErrorDto {
        val paper = paperService.retrievePaper(uploadFullPaperDto.paper) ?: return ErrorDto(false, "Invalid paper ID!")
        val token = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken;
        val author = token.principal as Account

        if (!paperService.validateFullPaperUpload(author, paper)) {
            return ErrorDto(false, "Invalid paper details!")
        }

        paper.fullPaper = fileUploadService.getFileUploadPath(uploadFullPaperDto.file)
        uploadPaperFile(paper, uploadFullPaperDto.file)

        return ErrorDto(true)
    }

    @PutMapping("/papers/uploadCameraReady")
    @PreAuthorize("hasRole('AUTHOR')")
    fun uploadPaperCameraReadyCopy(@ModelAttribute uploadFullPaperDto: UploadFullPaperDto): ErrorDto {
        val paper = paperService.retrievePaper(uploadFullPaperDto.paper) ?: return ErrorDto(false, "Invalid paper")
        val token = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken;
        val author = token.principal as Account

        if (!paperService.validatePaperCameraReadyCopyUpload(author, paper)) {
            return ErrorDto(false, "Invalid paper details!")
        }

        paper.cameraReadyCopy = fileUploadService.getFileUploadPath(uploadFullPaperDto.file)
        uploadPaperFile(paper, uploadFullPaperDto.file)

        return ErrorDto(true)
    }

    private fun getRequestedPapers(author: Account, getPapers: (author: Account) -> Collection<Paper>, validDeadlines: (deadlines: ConferenceDeadlines?) -> Boolean): Collection<PaperFromAuthorDto> {
        val papersDtoSet: MutableSet<PaperFromAuthorDto> = mutableSetOf()
        getPapers(author).forEach{ paper ->
            val topic: String = paper.topicOfInterest.name
            val decision: Boolean = paper.decision == PaperDecision.ACCEPTED
            val conferenceDeadlines = paper.conference.conferenceDeadlines

            // only the papers assigned to conferences whose paper submission deadline is still valid should be considered
            if (validDeadlines(conferenceDeadlines)) {
                papersDtoSet.add(PaperFromAuthorDto(paper.id, paper.title, paper.abstract, accountsService.convertToAccountUserDataDtos(paper.paperAuthors),
                    paper.keywords, topic, paper.conference.name)
                )
            }
        }
        return papersDtoSet
    }

    private fun uploadPaperFile(paper: Paper, file: MultipartFile) {
        paperService.addPaper(paper)
        fileUploadService.uploadFile(file)
    }
}
