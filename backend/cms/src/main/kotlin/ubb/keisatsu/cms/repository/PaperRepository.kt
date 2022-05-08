package ubb.keisatsu.cms.repository;

import org.springframework.data.repository.CrudRepository
import ubb.keisatsu.cms.model.entities.Paper
import java.util.*

interface PaperRepository : CrudRepository<Paper, Int> {
    override fun findById(paperId: Int): Optional<Paper>


    fun findByTitle(title: String): Iterable<Paper>

}