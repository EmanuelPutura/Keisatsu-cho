package ubb.keisatsu.cms.model

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object ConferenceTopicTable: Table<Nothing>("ConferenceTopic"){
    val conferenceID=int("ConferenceID").primaryKey()
    val topicID=int("TopicID").primaryKey()
}

data class ConferenceTopic(val conference: Int, val topic: Int)