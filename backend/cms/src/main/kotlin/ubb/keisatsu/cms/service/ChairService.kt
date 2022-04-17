package ubb.keisatsu.cms.service

import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.Conference
import ubb.keisatsu.cms.repository.Repository

@Service
class ChairService(private val conferencesRepository: Repository<Conference>) {
    fun addConference(conference: Conference): Unit = conferencesRepository.add(conference)
}