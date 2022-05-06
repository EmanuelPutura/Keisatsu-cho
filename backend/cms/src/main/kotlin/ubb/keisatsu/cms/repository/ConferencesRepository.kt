package ubb.keisatsu.cms.repository

import org.springframework.data.repository.CrudRepository
import ubb.keisatsu.cms.model.entities.Conference

interface ConferencesRepository : CrudRepository<Conference, Int> {
    fun findByMainOrganiserId(organiserId: Int): Iterable<Conference>
}
