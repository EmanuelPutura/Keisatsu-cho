package ubb.keisatsu.cms.repository

import org.ktorm.dsl.Query


interface Repository<T> {
    fun add(entity: T): Unit

    fun update(entity: T): Unit

    fun delete(entity: T): Unit
    
    fun retrieveAll(): Collection<T>


}