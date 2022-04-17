package ubb.keisatsu.cms.repository.database

import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.select
import ubb.keisatsu.cms.model.Conference
import ubb.keisatsu.cms.model.ConferenceTable
import ubb.keisatsu.cms.repository.Repository
import ubb.keisatsu.cms.repository.memory.MemoryRepository

@org.springframework.stereotype.Repository
class DbConferencesRepository : MemoryRepository<Conference>() {
    private lateinit var database: Database

    init {
        connect()
    }

    private fun connect(): Unit {
        database = Database.connect("jdbc:postgresql://localhost:5432/CMS", user = "postgres", password = "postgres")
        for (row in database.from(ConferenceTable).select()){
            println(row[ConferenceTable.name])
        }
    }

    private fun load(): Unit {
        // TODO
    }


    override fun add(entity: Conference) {
        database.insert(ConferenceTable) {
            set(it.name, entity.name)
            set(it.url, entity.url)
            set(it.mainOrganiserId, entity.organizerId)
            set(it.deadlinesID, entity.deadlinesId)
        }
    }

    override fun update(entity: Conference) {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Conference) {
    }

    override fun retrieveAll(): Collection<Conference> {
        return entities
    }
}