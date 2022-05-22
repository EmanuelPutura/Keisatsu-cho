package ubb.keisatsu.cms.repository

import org.springframework.data.repository.CrudRepository
import ubb.keisatsu.cms.model.entities.Review
import ubb.keisatsu.cms.model.entities.ReviewStatus

interface ReviewRepository : CrudRepository<Review, Int> {
    fun findByReviewerIdAndReviewStatus(reviewerId: Int, reviewStatus: ReviewStatus): Iterable<Review>
    fun findByPaperIdAndReviewStatus(paperId: Int, reviewStatus: ReviewStatus): Iterable<Review>
    fun findByReviewerIdAndPaperId(reviewerId: Int, paperId: Int): Review
}