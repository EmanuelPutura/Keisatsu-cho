package ubb.keisatsu.cms.repository;

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import ubb.keisatsu.cms.model.entities.Conference
import ubb.keisatsu.cms.model.entities.Paper
import java.util.*

interface PaperRepository : CrudRepository<Paper, Int> {
    override fun findById(paperId: Int): Optional<Paper>

    @Query("select p from Paper p")
    override fun findAll(): Iterable<Paper>

    fun findByTitle(title: String): Iterable<Paper>

    fun findByConferenceId(conferenceId: Int): Iterable<Paper>
}