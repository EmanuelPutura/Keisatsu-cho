package ubb.keisatsu.cms.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.entities.TopicOfInterest
import ubb.keisatsu.cms.repository.TopicsOfInterestRepository

@Service
class TopicsOfInterestService(private val topicsOfInterestRepository: TopicsOfInterestRepository) {
    fun convertTopicsArrayToString(topicsOfInterest: Iterable<TopicOfInterest>): String {
        var result: String = ""
        topicsOfInterest.forEach { topic ->
            result = result.plus(topic.name + "\n")
        }

        return result
    }

    fun getTopicsArrayFromString(topics: String): Iterable<TopicOfInterest>{
        val result: MutableList<TopicOfInterest> = mutableListOf()
        topics.split(";").forEach{ topic ->
            val topicOfInterest: TopicOfInterest = retrieveTopicOfInterest(topic) ?: return@forEach
            result.add(topicOfInterest)
        }
        return result
    }

    fun findAllForConference(conferenceId: Int): Iterable<TopicOfInterest> = topicsOfInterestRepository.findByConferencesForTopicId(conferenceId)

    fun findAllForAccount(accountId: Int): Iterable<TopicOfInterest> = topicsOfInterestRepository.findByAccountsForTopicId(accountId)


    fun addTopicOfInterest(topicOfInterest: TopicOfInterest): TopicOfInterest = topicsOfInterestRepository.save(topicOfInterest)

    fun retrieveTopicOfInterest(name: String): TopicOfInterest? = topicsOfInterestRepository.findByName(name)

    fun retrieveAll(): Iterable<TopicOfInterest> = topicsOfInterestRepository.findAll()

    fun findTopicNameById(id: Int): TopicOfInterest? = topicsOfInterestRepository.findByIdOrNull(id)
}
