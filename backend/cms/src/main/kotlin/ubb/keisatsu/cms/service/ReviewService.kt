package ubb.keisatsu.cms.service

import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.entities.Account
import ubb.keisatsu.cms.model.entities.Review
import ubb.keisatsu.cms.model.entities.ReviewStatus
import ubb.keisatsu.cms.repository.ReviewRepository

@Service
class ReviewService(private val reviewRepository: ReviewRepository) {
    fun getPendingReviewsOfReviewer(reviewerId: Int): Int {
        return reviewRepository.findByReviewerIdAndReviewStatus(reviewerId, ReviewStatus.PENDING).count()
    }

    fun addReview(review: Review) = reviewRepository.save(review)

    fun retrieveReview(reviewerId: Int, paperId: Int): Review {
        return reviewRepository.findByReviewerIdAndPaperId(reviewerId,paperId)
    }

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

    fun deleteReview(review: Review) {
        reviewRepository.delete(review)
    }

    fun getFirstBid(paperID: Int): Review? {
        val reviews =  reviewRepository.findByPaperIdAndReviewStatus(paperID, ReviewStatus.BID)
        if(reviews.count() == 0)
            return null
        return reviews.first()
    }

    fun notInConflict(reviewerId: Int, paperId: Int): Boolean {
        reviewRepository.findByPaperIdAndReviewStatus(paperId, ReviewStatus.CONFLICT).forEach{ review ->
            if(review.reviewer.id == reviewerId)
                return false
        }
        return true

    }
}