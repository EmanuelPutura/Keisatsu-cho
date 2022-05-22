package ubb.keisatsu.cms.model.entities

import ubb.keisatsu.cms.model.AbstractJpaHashable
import javax.persistence.*

enum class PaperDecision {
    NOT_YET_DECIDED, ACCEPTED, REJECTED
}

@Entity
@Table(name = "\"Paper\"")
class Paper (
    @Column(name = "\"Title\"", length = 64)
    var title: String,

    @Column(name = "\"Keywords\"", length = 128)
    var keywords: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"TopicID\"", nullable = false)
    var topicOfInterest: TopicOfInterest,

    @Column(name="Abstract")
    var abstract: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ConferenceID\"", nullable = false)
    var conference: Conference,

    @Column(name = "\"FullPaper\"", nullable = true)
    var fullPaper: String? = null,

    @Column(name = "\"CameraReadyCopy\"", nullable = true)
    var cameraReadyCopy: String? = null,

    @ManyToOne
    @JoinColumn(name = "AcceptRejectDecisionMaker", referencedColumnName = "AccountID")
    var decisionMaker: Account? = null,

    @ManyToOne
    @JoinColumn(name = "reviewer", referencedColumnName = "AccountID")
    var reviewer: Account? = null,

    @Column(name="decision")
    var decision: PaperDecision = PaperDecision.NOT_YET_DECIDED,

    @ManyToMany
    @JoinTable(name="PaperAuthor")
    var paperAuthors: MutableSet<Account> = mutableSetOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"PaperID\"", nullable = false)
    var id: Int = -1
) : AbstractJpaHashable()