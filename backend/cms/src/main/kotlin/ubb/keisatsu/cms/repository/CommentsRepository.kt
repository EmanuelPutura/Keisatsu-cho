package ubb.keisatsu.cms.repository

import org.springframework.data.repository.CrudRepository
import ubb.keisatsu.cms.model.entities.Comment

interface CommentsRepository : CrudRepository<Comment, Int> {
    fun findByPaperId(paperId: Int): Iterable<Comment>
}