package ubb.keisatsu.cms.service

import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.entities.Review
import ubb.keisatsu.cms.model.entities.ReviewStatus
import ubb.keisatsu.cms.repository.ReviewRepository

@Service
class ReviewService(private val reviewRepository: ReviewRepository) {
    fun getPendingReviewsOfReviewer(reviewerId: Int): Int {
        return reviewRepository.findByReviewerIdAndReviewStatus(reviewerId, ReviewStatus.PENDING).count()
    }

    fun addReview(review: Review) = reviewRepository.save(review)
    fun isDecisionNotTaken(paperId: Int): Boolean {
        return reviewRepository.findByPaperIdAndReviewStatus(paperId, ReviewStatus.PENDING).count() != 0
    }

    fun acceptPaper(reviewerId: Int, paperId: Int) {
        val paper = reviewRepository.findByReviewerIdAndPaperId(reviewerId,paperId)
        paper.reviewStatus = ReviewStatus.ACCEPTED
        reviewRepository.save(paper)
    }

    fun rejectPaper(reviewerId: Int, paperId: Int) {
        val paper = reviewRepository.findByReviewerIdAndPaperId(reviewerId,paperId)
        paper.reviewStatus = ReviewStatus.REJECTED
        reviewRepository.save(paper)
    }

    fun notReviewed(paperId: Int, reviewerId: Int): Boolean {
        return reviewRepository.findByReviewerIdAndPaperId(reviewerId,paperId).reviewStatus == ReviewStatus.PENDING
    }
}