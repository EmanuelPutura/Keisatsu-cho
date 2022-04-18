package ubb.keisatsu.cms.repository.database


import org.ktorm.database.Database
import org.ktorm.dsl.insert
import ubb.keisatsu.cms.model.Chair
import ubb.keisatsu.cms.model.ChairTable
import ubb.keisatsu.cms.repository.memory.MemoryRepository

@org.springframework.stereotype.Repository
class DbChairsRepository(val dbConfig: DatabaseConfig) : MemoryRepository<Chair>() {
    private lateinit var database: Database

    init {
        connect()
    }

    private fun connect(): Unit {
        database = Database.connect(url = dbConfig.url, user = dbConfig.user, password = dbConfig.password)
    }

    private fun load(): Unit {
        // TODO
    }


    override fun add(entity: Chair) {
        database.insert(ChairTable) {
            set(it.id, entity.id)
        }
    }

    override fun update(entity: Chair) {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Chair) {
    }

    override fun retrieveAll(): Collection<Chair> {
        return entities
    }
}
