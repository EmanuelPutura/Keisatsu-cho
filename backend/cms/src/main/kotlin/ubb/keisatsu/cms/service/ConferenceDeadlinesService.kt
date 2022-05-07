package ubb.keisatsu.cms.service

import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.entities.ConferenceDeadlines
import ubb.keisatsu.cms.repository.ConferenceDeadlinesRepository

@Service
class ConferenceDeadlinesService(private val conferenceDeadlinesRepository: ConferenceDeadlinesRepository) {
    fun addConferenceDeadlines(conferenceDeadlines: ConferenceDeadlines): ConferenceDeadlines = conferenceDeadlinesRepository.save(conferenceDeadlines)
}
