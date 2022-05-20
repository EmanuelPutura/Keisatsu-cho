package ubb.keisatsu.cms.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ubb.keisatsu.cms.model.dto.ConferenceDetailsDto
import ubb.keisatsu.cms.model.dto.PaperFromAuthorDto
import ubb.keisatsu.cms.model.entities.Account
import ubb.keisatsu.cms.model.entities.UserRole
import ubb.keisatsu.cms.service.AccountsService
import ubb.keisatsu.cms.service.ConferencesService
import ubb.keisatsu.cms.service.PaperService
import ubb.keisatsu.cms.service.TopicsOfInterestService
import java.time.format.DateTimeFormatter

@RestController
@CrossOrigin
class AuthorController(private val conferencesService: ConferencesService, private val topicsOfInterestService: TopicsOfInterestService,
                       private val accountsService: AccountsService, private val paperService: PaperService) {

    private val ACCEPTED_PAPER_REQUEST_TYPE: String = "accepted"
    private val MISSING_FULL_PAPER_REQUEST_TYPE: String = "missingFull";

    @GetMapping("conferences/all")
    fun getAllConferences(): MutableSet<ConferenceDetailsDto> {
        val conferenceDtoSet: MutableSet<ConferenceDetailsDto> = mutableSetOf()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        conferencesService.retrieveAll().forEach{ conference ->
            val topics: String = topicsOfInterestService.convertTopicsArrayToString(topicsOfInterestService.findAllForConference(conference.id))
            conferenceDtoSet.add(
                ConferenceDetailsDto(conference.name, conference.url, conference.subtitles, topics,
                    conference.conferenceDeadlines?.paperSubmissionDeadline?.format(formatter), conference.conferenceDeadlines?.paperReviewDeadline?.format(formatter),
                    conference.conferenceDeadlines?.acceptanceNotificationDeadline?.format(formatter), conference.conferenceDeadlines?.acceptedPaperUploadDeadline?.format(formatter))
            )
        }

        return conferenceDtoSet;
    }

    @GetMapping("/papers")
    fun getPapers(@RequestParam token: Int, @RequestParam type: String): Collection<PaperFromAuthorDto> {
        val account = accountsService.retrieveAccount(token) ?: return mutableSetOf()  // return an empty set
        if (account.role != UserRole.AUTHOR) {
            return mutableSetOf();  // return an empty set
        }

        if (ACCEPTED_PAPER_REQUEST_TYPE == type) {
            return getAcceptedPapers(account)
        }
        else if (MISSING_FULL_PAPER_REQUEST_TYPE == type) {
            return getMissingFullPapers(account)
        }

        return mutableSetOf();  // return an empty set
    }

    private fun getAcceptedPapers(account: Account): Collection<PaperFromAuthorDto> {
        val papersDtoSet: MutableSet<PaperFromAuthorDto> = mutableSetOf()
        paperService.retrieveNotUploadedPapersHavingAuthor(account).forEach{ paper ->
            val topic: String = paper.topicID!!.name

//            papersDtoSet.add(PaperFromAuthorDto(paper.id, paper.title, paper.abstract, accountsService.convertToAccountUserDataDtos(paper.paperAuthors),
//                paper.keywords, topic, conferenceID)
//            )
        }
        return papersDtoSet
    }

    private fun getMissingFullPapers(account: Account): Collection<PaperFromAuthorDto> {
        val papersDtoSet: MutableSet<PaperFromAuthorDto> = mutableSetOf()
//        paperService.retrieveAll().forEach{ paper ->
//            val topic: String = paper.topicID!!.name
//            val conferenceID = paper.conferenceId!!.id
//
//            papersDtoSet.add(
//                PaperDetailsDto(paper.id, paper.title, paper.abstract, accountsService.convertToAccountUserDataDtos(paper.paperAuthors),
//                paper.keywords, topic, conferenceID)
//            )
//        }
        return papersDtoSet
    }
}
