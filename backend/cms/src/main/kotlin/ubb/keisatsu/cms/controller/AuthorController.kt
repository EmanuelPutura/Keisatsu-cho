package ubb.keisatsu.cms.controller

import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ubb.keisatsu.cms.model.dto.*
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

    @PostMapping("/papers")
    fun submitPaper(@RequestBody submittedPaperDetailsDto: SubmittedPaperDetailsDto): ErrorDto {
        val conference = conferencesService.retrieveConference(submittedPaperDetailsDto.conference) ?: return ErrorDto(false, "Invalid conference ID!")
        val topicOfInterest = topicsOfInterestService.retrieveTopicOfInterest(submittedPaperDetailsDto.interestTopic) ?: return ErrorDto(false, "Invalid topic of interest ID!")

        val paper = Paper(submittedPaperDetailsDto.title, submittedPaperDetailsDto.keywords, topicOfInterest, submittedPaperDetailsDto.abstract, conference)
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

        paperService.addPaper(paper)
        return ErrorDto(true)
    }

    @PutMapping("/papers/uploadPaper")
    fun uploadFullPaper(@ModelAttribute uploadFullPaperDto: UploadFullPaperDto): ErrorDto {
        val paper = paperService.retrievePaper(uploadFullPaperDto.paper) ?: return ErrorDto(false, "Invalid paper ID!")
        val author = accountsService.retrieveAccount(uploadFullPaperDto.token) ?: return ErrorDto(false, "Invalid paper author ID!")

        if (!paperService.validateFullPaperUpload(author, paper)) {
            return ErrorDto(false, "Invalid paper details!")
        }

        paper.fullPaper = fileUploadService.getFileUploadPath(uploadFullPaperDto.file)
        uploadPaperFile(paper, uploadFullPaperDto.file)

        return ErrorDto(true)
    }

    @PutMapping("/papers/uploadCameraReady")
    fun uploadPaperCameraReadyCopy(@ModelAttribute uploadFullPaperDto: UploadFullPaperDto): ErrorDto {
        val paper = paperService.retrievePaper(uploadFullPaperDto.paper) ?: return ErrorDto(false, "Invalid paper ID!")
        val author = accountsService.retrieveAccount(uploadFullPaperDto.token) ?: return ErrorDto(false, "Invalid paper author ID!")

        if (!paperService.validatePaperCameraReadyCopyUpload(author, paper)) {
            return ErrorDto(false, "Invalid paper details!")
        }

        paper.cameraReadyCopy = fileUploadService.getFileUploadPath(uploadFullPaperDto.file)
        uploadPaperFile(paper, uploadFullPaperDto.file)

        return ErrorDto(true)
    }

    private fun uploadPaperFile(paper: Paper, file: MultipartFile) {
        paperService.addPaper(paper)
        fileUploadService.uploadFile(file)
    }
}
