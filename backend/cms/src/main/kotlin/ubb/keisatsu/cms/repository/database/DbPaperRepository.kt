package ubb.keisatsu.cms.repository.database

import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.select
import ubb.keisatsu.cms.model.Paper
import ubb.keisatsu.cms.model.PaperTable
import ubb.keisatsu.cms.repository.memory.MemoryRepository

class DbPaperRepository: MemoryRepository<Paper>() {
    private lateinit var database: Database

    init {
        connect()
    }

    private fun connect(): Unit {
        database = Database.connect("jdbc:postgresql://localhost:5432/CMS", user = "postgres", password = "postgres")
        for (row in database.from(PaperTable).select()){
            println(row[PaperTable.title])
        }
    }

    private fun load(): Unit {
        // TODO
    }


    override fun add(entity: Paper) {
        database.insert(PaperTable) {
            set(it.title,entity.title)
            set(it.keywords,entity.keywords)
            set(it.topic,entity.topic)
            set(it.format,entity.format)
        }
    }

    override fun update(entity: Paper) {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Paper) {
    }

    override fun retrieveAll(): Collection<Paper> {
        return entities
    }
}