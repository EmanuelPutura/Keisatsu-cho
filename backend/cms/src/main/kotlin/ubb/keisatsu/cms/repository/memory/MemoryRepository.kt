package ubb.keisatsu.cms.repository.memory

import ubb.keisatsu.cms.repository.Repository

// TODO: make all entities inherit from a BaseID<ID, T> class, use ID for queries
@org.springframework.stereotype.Repository
open class MemoryRepository<T>: Repository<T> {
    protected var entities: MutableList<T> = mutableListOf()

    override fun add(entity: T) {
        // TODO: check for the account not to already be present in the repository
        entities.add(entity)
    }

    override fun update(entity: T) {
        TODO("Not yet implemented")
    }

    override fun delete(entity: T) {
        entities.remove(entity)
    }

    override fun retrieveAll(): Collection<T> {
        return entities
    }
}