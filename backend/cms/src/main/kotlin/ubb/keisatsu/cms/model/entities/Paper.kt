package ubb.keisatsu.cms.model.entities

import ubb.keisatsu.cms.model.AbstractJpaHashable
import javax.persistence.*

@Entity
@Table(name = "\"Paper\"")
class Paper (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"PaperID\"", nullable = false)
    var id: Int = -1,

    @Column(name = "\"Title\"", length = 64)
    var title: String ,

    @Column(name = "\"Keywords\"", length = 128)
    var keywords: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"TopicID\"")
    var topicID: TopicOfInterest? = null,

    @Column(name = "\"Format\"", nullable = false, length = 32)
    var format: String,

    @Column(name = "\"File\"", nullable = false)
    var file: ByteArray,

    @ManyToMany(mappedBy = "assignedPapers")
    var papersForConference: MutableSet<Conference> = mutableSetOf(),

    @OneToMany(mappedBy = "paper")
    var evaluation: MutableSet<ChairPaperEvaluation> = mutableSetOf()

) : AbstractJpaHashable()