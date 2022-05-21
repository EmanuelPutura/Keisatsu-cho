package ubb.keisatsu.cms.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.entities.ConferenceDeadlines
import ubb.keisatsu.cms.repository.ConferenceDeadlinesRepository
import java.time.LocalDate

@Service
class ConferenceDeadlinesService(private val conferenceDeadlinesRepository: ConferenceDeadlinesRepository) {
    fun retrieveConferenceDeadlines(conferenceDeadlinesId: Int): ConferenceDeadlines? = conferenceDeadlinesRepository.findByIdOrNull(conferenceDeadlinesId)

    fun addConferenceDeadlines(conferenceDeadlines: ConferenceDeadlines): ConferenceDeadlines = conferenceDeadlinesRepository.save(conferenceDeadlines)

    fun validateDeadlines(submissionDeadline: LocalDate, reviewDeadline: LocalDate, acceptanceDeadline: LocalDate, uploadDeadline: LocalDate): Boolean {
        return submissionDeadline.isBefore(reviewDeadline) && reviewDeadline.isBefore(acceptanceDeadline) && acceptanceDeadline.isBefore(uploadDeadline) &&
                LocalDate.now().isBefore(submissionDeadline)
    }

    fun isDeadlineStillValid(deadline: LocalDate): Boolean {
        return LocalDate.now().isBefore(deadline)
    }
}
