package ubb.keisatsu.cms.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ubb.keisatsu.cms.model.dto.ConferenceDetailsDto
import ubb.keisatsu.cms.service.ConferencesService
import ubb.keisatsu.cms.service.TopicsOfInterestService
import java.time.format.DateTimeFormatter

@RestController
@CrossOrigin
class AuthorController(private val conferencesService: ConferencesService, private val topicsOfInterestService: TopicsOfInterestService) {
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
    fun getPapers(@RequestParam token: Int, @RequestParam type: String) {
    }

    private fun getAcceptedPapers(token: Int) {

    }

    private fun getMissingFullPapers(token: Int) {

    }
}
