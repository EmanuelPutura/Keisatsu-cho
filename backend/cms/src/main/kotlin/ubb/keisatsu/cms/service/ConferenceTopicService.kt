package ubb.keisatsu.cms.service

import ubb.keisatsu.cms.model.Conference
import ubb.keisatsu.cms.model.ConferenceTopic
import ubb.keisatsu.cms.model.TopicOfInterest
import ubb.keisatsu.cms.repository.Repository
import ubb.keisatsu.cms.repository.database.DbConferenceTopicRepository
import ubb.keisatsu.cms.repository.database.DbConferencesRepository
import ubb.keisatsu.cms.repository.database.DbTopicOfInterestRepository

class ConferenceTopicService(private val conferenceTopicRepository: Repository<ConferenceTopic>) {
    fun addConferenceTopic(conferenceTopic: ConferenceTopic): Unit=conferenceTopicRepository.add(conferenceTopic)
}