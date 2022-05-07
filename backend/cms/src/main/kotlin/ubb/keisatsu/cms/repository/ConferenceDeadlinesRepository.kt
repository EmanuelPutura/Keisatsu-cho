package ubb.keisatsu.cms.repository

import org.springframework.data.repository.CrudRepository
import ubb.keisatsu.cms.model.entities.ConferenceDeadlines

interface ConferenceDeadlinesRepository : CrudRepository<ConferenceDeadlines, Int> {
}