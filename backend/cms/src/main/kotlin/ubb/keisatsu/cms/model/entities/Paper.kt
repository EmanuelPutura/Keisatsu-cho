package ubb.keisatsu.cms.model.entities

import javax.persistence.*

@Entity
@Table(name = "\"Paper\"")
class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"PaperID\"", nullable = false)
    var id: Int? = null

    @Column(name = "\"Title\"", length = 64)
    var title: String? = null

    @Column(name = "\"Keywords\"", length = 128)
    var keywords: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"TopicID\"")
    var topicID: TopicOfInterest? = null

    @Column(name = "\"Format\"", nullable = false, length = 32)
    var format: String? = null

    @Column(name = "\"File\"", nullable = false)
    var file: ByteArray? = null

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "paper")
    var chairPaperEvaluation: ChairPaperEvaluation? = null

    @OneToMany(mappedBy = "paperID")
    var paperConferences: MutableSet<PaperConference> = mutableSetOf()
}