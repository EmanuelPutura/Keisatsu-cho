package ubb.keisatsu.cms.model

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object TopicTable: Table<Nothing>("TopicOfInterest"){
    val id=int("TopicID").primaryKey()
    val name=varchar("Name")
}

data class TopicOfInterest(val name: String)