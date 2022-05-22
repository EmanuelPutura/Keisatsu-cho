package ubb.keisatsu.cms.service

import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.entities.Comment
import ubb.keisatsu.cms.repository.CommentsRepository

@Service
class CommentService(private val commentsRepository: CommentsRepository) {

    fun retrieveCommentsForPaper(paperId: Int): Iterable<Comment> = commentsRepository.findByPaperId(paperId)

    fun addComment(comment: Comment): Comment = commentsRepository.save(comment)
}