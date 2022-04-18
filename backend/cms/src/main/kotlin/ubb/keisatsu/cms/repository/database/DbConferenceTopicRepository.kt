package ubb.keisatsu.cms.repository.database

import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.select
import ubb.keisatsu.cms.model.ConferenceTopic
import ubb.keisatsu.cms.model.ConferenceTopicTable
import ubb.keisatsu.cms.repository.memory.MemoryRepository

class DbConferenceTopicRepository(val dbConfig: DatabaseConfig): MemoryRepository<ConferenceTopic>(){
    private lateinit var database: Database

    init {
        connect()
    }

    private fun connect(): Unit {
        database = Database.connect(dbConfig.url, user = dbConfig.user, password = dbConfig.password)

    }

    private fun load(): Unit {
        // TODO
    }



    override fun add(entity: ConferenceTopic) {
        database.insert(ConferenceTopicTable) {
            set(it.conferenceID, entity.conference)
            set(it.topicID,entity.topic)
        }
        super.add(entity)
    }

    override fun update(entity: ConferenceTopic) {
        TODO("Not yet implemented")
    }

    override fun delete(entity: ConferenceTopic) {
        entities.remove(entity)
    }

    override fun retrieveAll(): Collection<ConferenceTopic> {
        return entities
    }
}