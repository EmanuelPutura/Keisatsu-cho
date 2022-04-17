package ubb.keisatsu.cms.model

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

// TODO: set FKs
object ConferenceTable : Table<Nothing>("Conference") {
    val id = int("ConferenceID").primaryKey()
    val name = varchar("Name")
    val url = varchar("url")
    val mainOrganiserId = int("MainOrganiserID")
    val deadlinesID = int("DeadlinesID")
}

data class ConferenceDTO(val email: String, val name: String, val subtitles: String, val url: String)

data class Conference(val name: String, val url: String, val organizerId: Int, val deadlinesId: Int)
