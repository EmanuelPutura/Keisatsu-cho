package ubb.keisatsu.cms.repository.database;

import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.springframework.stereotype.Repository
import ubb.keisatsu.cms.model.TopicOfInterest
import ubb.keisatsu.cms.model.TopicTable
import ubb.keisatsu.cms.repository.memory.MemoryRepository

@Repository
class DbTopicOfInterestRepository: MemoryRepository<TopicOfInterest>(){
    private lateinit var database: Database

    init {
        connect()
    }

    private fun connect(): Unit {
        database = Database.connect("jdbc:postgresql://localhost:5432/cms", user = "postgres", password = "postgres")
        for (row in database.from(TopicTable).select()){
            println(row[TopicTable.name])
        }
    }

    private fun load(): Unit {
        // TODO
    }

//    fun findTopic(name: String): Collection<TopicOfInterest> {
//        return database.from(TopicTable)
//                .select()
//                .map{row ->  TopicOfInterest(row[TopicTable.name])}
//                .filter(it)
//    }

    override fun add(entity: TopicOfInterest) {
        database.insert(TopicTable) {
            set(it.name, entity.name)
        }
        super.add(entity)
    }

    override fun update(entity: TopicOfInterest) {
        TODO("Not yet implemented")
    }

    override fun delete(entity: TopicOfInterest) {
        entities.remove(entity)
    }

    override fun retrieveAll(): Collection<TopicOfInterest> {
        return entities
    }
}
