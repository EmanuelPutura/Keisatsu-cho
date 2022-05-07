package ubb.keisatsu.cms.repository;

import org.springframework.data.repository.CrudRepository
import ubb.keisatsu.cms.model.entities.Paper

interface PaperRepository : CrudRepository<Paper, Int> {
    fun findByPaperId(paperId: Int): Iterable<Paper>
}