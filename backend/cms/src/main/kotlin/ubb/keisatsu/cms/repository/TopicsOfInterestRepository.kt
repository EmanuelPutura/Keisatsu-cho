package ubb.keisatsu.cms.repository

import org.springframework.data.repository.CrudRepository
import ubb.keisatsu.cms.model.entities.TopicOfInterest

interface TopicsOfInterestRepository : CrudRepository<TopicOfInterest, Int> {
    fun findByConferencesForTopicId(conferenceId: Int): Iterable<TopicOfInterest>
}