package ubb.keisatsu.cms.service

import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.entities.Conference
import ubb.keisatsu.cms.repository.ConferencesRepository

@Service
class ConferencesService(private val conferencesRepository: ConferencesRepository) {
    fun addConference(conference: Conference): Conference = conferencesRepository.save(conference)

    fun retrieveAll(): Iterable<Conference> = conferencesRepository.findAll()
}