package ubb.keisatsu.cms.service

import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.TopicOfInterest
import ubb.keisatsu.cms.repository.Repository

@Service
class TopicOfInterestService (private val topicRepository: Repository<TopicOfInterest>){
    fun addTopic(topic: TopicOfInterest): Unit = topicRepository.add(topic)
}