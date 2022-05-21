package ubb.keisatsu.cms.controller

import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ubb.keisatsu.cms.model.dto.ConferenceDto
import ubb.keisatsu.cms.model.dto.PaperFromAuthorDto
import ubb.keisatsu.cms.model.dto.SubmittedPaperDetailsDto
import ubb.keisatsu.cms.model.dto.UploadFullPaperDto
import ubb.keisatsu.cms.model.entities.*
import ubb.keisatsu.cms.service.*
import java.time.format.DateTimeFormatter

@RestController
@CrossOrigin
class AuthorController(private val conferencesService: ConferencesService, private val topicsOfInterestService: TopicsOfInterestService,
                       private val accountsService: AccountsService, private val paperService: PaperService, private val fileUploadService: FileUploadService,
                       private val conferenceDeadlinesService: ConferenceDeadlinesService) {

    private val ACCEPTED_PAPER_REQUEST_TYPE: String = "accepted"
    private val MISSING_FULL_PAPER_REQUEST_TYPE: String = "missingFull"

    @GetMapping("conferences/all")
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
    fun getPapers(@RequestParam token: Int, @RequestParam type: String): Collection<PaperFromAuthorDto> {
        val account = accountsService.retrieveAccount(token) ?: return mutableSetOf()  // return an empty set
        if (account.role != UserRole.AUTHOR) {
            return mutableSetOf()  // return an empty set
        }

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

    private fun getRequestedPapers(author: Account, getPapers: (author: Account) -> Collection<Paper>, validDeadlines: (deadlines: ConferenceDeadlines?) -> Boolean): Collection<PaperFromAuthorDto> {
        val papersDtoSet: MutableSet<PaperFromAuthorDto> = mutableSetOf()
        getPapers(author).forEach{ paper ->
            val topic: String = paper.topicOfInterest.name
            val decision: Boolean = paper.decision == PaperDecision.ACCEPTED
            val conferenceDeadlines = paper.conference.conferenceDeadlines

            // only the papers assigned to conferences whose paper submission deadline is still valid should be considered
            if (validDeadlines(conferenceDeadlines)) {
                papersDtoSet.add(PaperFromAuthorDto(paper.id, paper.title, paper.abstract, accountsService.convertToAccountUserDataDtos(paper.paperAuthors),
                    paper.keywords, topic, decision)
                )
            }
        }
        return papersDtoSet
    }

    // TODO: check author token
    @PostMapping("/papers")
    fun submitPaper(@RequestBody submittedPaperDetailsDto: SubmittedPaperDetailsDto): Boolean {
        val conference = conferencesService.retrieveConference(submittedPaperDetailsDto.conference) ?: return false
        val topicOfInterest = topicsOfInterestService.retrieveTopicOfInterest(submittedPaperDetailsDto.interestTopic) ?: return false

        val paper = Paper(submittedPaperDetailsDto.title, submittedPaperDetailsDto.keywords, topicOfInterest, submittedPaperDetailsDto.abstract, conference)
        submittedPaperDetailsDto.authors.forEach{ authorDtos ->
            val author = accountsService.retrieveAccount(authorDtos.email) ?: return false
            if (author.role != UserRole.AUTHOR) {
                return false
            }

            paper.paperAuthors.add(author)
        }

        paperService.addPaper(paper)
        return true
    }

    @PutMapping("/papers/uploadPaper")
    fun uploadFullPaper(@ModelAttribute uploadFullPaperDto: UploadFullPaperDto): Boolean {
        val paper = paperService.retrievePaper(uploadFullPaperDto.paper) ?: return false
        val author = accountsService.retrieveAccount(uploadFullPaperDto.token) ?: return false

        if (!paperService.validateFullPaperUpload(author, paper)) {
            return false
        }

        paper.fullPaper = fileUploadService.getFileUploadPath(uploadFullPaperDto.file)
        uploadPaperFile(paper, uploadFullPaperDto.file)

        return true
    }

    @PutMapping("/papers/uploadCameraReady")
    fun uploadPaperCameraReadyCopy(@ModelAttribute uploadFullPaperDto: UploadFullPaperDto): Boolean {
        val paper = paperService.retrievePaper(uploadFullPaperDto.paper) ?: return false
        val author = accountsService.retrieveAccount(uploadFullPaperDto.token) ?: return false

        if (!paperService.validatePaperCameraReadyCopyUpload(author, paper)) {
            return false
        }

        paper.cameraReadyCopy = fileUploadService.getFileUploadPath(uploadFullPaperDto.file)
        uploadPaperFile(paper, uploadFullPaperDto.file)
        return true
    }

    private fun uploadPaperFile(paper: Paper, file: MultipartFile) {
        paperService.addPaper(paper)
        fileUploadService.uploadFile(file)
    }
}
