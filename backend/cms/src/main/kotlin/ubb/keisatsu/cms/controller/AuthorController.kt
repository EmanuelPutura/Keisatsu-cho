package ubb.keisatsu.cms.controller

import org.springframework.web.bind.annotation.*
import ubb.keisatsu.cms.model.dto.ConferenceDto
import ubb.keisatsu.cms.model.dto.PaperFromAuthorDto
import ubb.keisatsu.cms.model.dto.SubmittedPaperDetailsDto
import ubb.keisatsu.cms.model.dto.UploadFullPaperDto
import ubb.keisatsu.cms.model.entities.Account
import ubb.keisatsu.cms.model.entities.Paper
import ubb.keisatsu.cms.model.entities.PaperDecision
import ubb.keisatsu.cms.model.entities.UserRole
import ubb.keisatsu.cms.service.*
import java.time.format.DateTimeFormatter

@RestController
@CrossOrigin
class AuthorController(private val conferencesService: ConferencesService, private val topicsOfInterestService: TopicsOfInterestService,
                       private val accountsService: AccountsService, private val paperService: PaperService, private val fileUploadService: FileUploadService) {

    private val ACCEPTED_PAPER_REQUEST_TYPE: String = "accepted"
    private val MISSING_FULL_PAPER_REQUEST_TYPE: String = "missingFull";

    // TODO: check dates
    @GetMapping("conferences/all")
    fun getAllConferences(): MutableSet<ConferenceDto> {
        val conferenceDtoSet: MutableSet<ConferenceDto> = mutableSetOf()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        conferencesService.retrieveAll().forEach{ conference ->
            val topics: String = topicsOfInterestService.convertTopicsArrayToString(topicsOfInterestService.findAllForConference(conference.id))
            conferenceDtoSet.add(
                ConferenceDto(conference.id, conference.name, conference.url, conference.subtitles, topics,
                    conference.conferenceDeadlines?.paperSubmissionDeadline?.format(formatter), conference.conferenceDeadlines?.paperReviewDeadline?.format(formatter),
                    conference.conferenceDeadlines?.acceptanceNotificationDeadline?.format(formatter), conference.conferenceDeadlines?.acceptedPaperUploadDeadline?.format(formatter))
            )
        }

        return conferenceDtoSet;
    }

    // TODO: check dates
    @GetMapping("/papers")
    fun getPapers(@RequestParam token: Int, @RequestParam type: String): Collection<PaperFromAuthorDto> {
        val account = accountsService.retrieveAccount(token) ?: return mutableSetOf()  // return an empty set
        if (account.role != UserRole.AUTHOR) {
            return mutableSetOf();  // return an empty set
        }

        if (ACCEPTED_PAPER_REQUEST_TYPE == type) {
            return getRequestedPapers(account, paperService::retrievePapersHavingAuthorWithoutCameraReadyCopy)
        }
        else if (MISSING_FULL_PAPER_REQUEST_TYPE == type) {
            return getRequestedPapers(account, paperService::retrieveNotUploadedPapersHavingAuthor)
        }

        return mutableSetOf();  // return an empty set
    }

    // TODO: check dates
    private fun getRequestedPapers(author: Account, getPapers: (author: Account) -> Collection<Paper>): Collection<PaperFromAuthorDto> {
        val papersDtoSet: MutableSet<PaperFromAuthorDto> = mutableSetOf()
        getPapers(author).forEach{ paper ->
            val topic: String = paper.topicOfInterest!!.name
            val decision: Boolean = paper.decision == PaperDecision.ACCEPTED

            papersDtoSet.add(PaperFromAuthorDto(paper.id, paper.title, paper.abstract, accountsService.convertToAccountUserDataDtos(paper.paperAuthors),
                paper.keywords, topic, decision)
            )
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

        if (author.role != UserRole.AUTHOR || !paper.paperAuthors.contains(author)) {
            return false
        }

        paper.fullPaper = fileUploadService.getFileUploadPath(uploadFullPaperDto.file)
        paperService.addPaper(paper)
        fileUploadService.uploadFile(uploadFullPaperDto.file)

        return true
    }
}
