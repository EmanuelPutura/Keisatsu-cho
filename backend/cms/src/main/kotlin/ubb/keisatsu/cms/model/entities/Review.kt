package ubb.keisatsu.cms.model.entities

import ubb.keisatsu.cms.model.AbstractJpaHashable
import javax.persistence.*

enum class ReviewStatus{
    PENDING, CONFLICT, REJECTED, ACCEPTED, BID
}

@Entity
@Table(name = "Review")
class Review (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ReviewerID\"", nullable = false)
    var reviewer: Account,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"PaperID\"", nullable = false)
    var paper: Paper,

    @Column(name = "ReviewStatus")
    var reviewStatus: ReviewStatus = ReviewStatus.PENDING,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ReviewID")
    var id: Int = -1

) : AbstractJpaHashable()