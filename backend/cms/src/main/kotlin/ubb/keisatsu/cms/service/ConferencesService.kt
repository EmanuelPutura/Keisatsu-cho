package ubb.keisatsu.cms.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.entities.Conference
import ubb.keisatsu.cms.repository.ConferencesRepository

@Service
class ConferencesService(private val conferencesRepository: ConferencesRepository) {
    fun findByMainOrganiser(organiserId: Int): Iterable<Conference> = conferencesRepository.findByMainOrganiserId(organiserId)

    fun addConference(conference: Conference): Conference = conferencesRepository.save(conference)

    fun retrieveConference(id: Int): Conference? = conferencesRepository.findByIdOrNull(id)

    fun retrieveAll(): Iterable<Conference> = conferencesRepository.findAll()
}