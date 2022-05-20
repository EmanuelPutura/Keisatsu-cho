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
    var title: String,

    @Column(name = "\"Keywords\"", length = 128)
    var keywords: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"TopicID\"", nullable = false)
    var topicID: TopicOfInterest? = null,

    @Column(name = "\"Format\"", nullable = true, length = 32)
    var format: String,

    @Column(name = "\"File\"", nullable = true)
    var file: ByteArray?,

    @Column(name="Abstract")
    var abstract: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ConferenceID\"", nullable = false)
    var conferenceId: Conference? = null,

    @OneToMany(mappedBy = "paper")
    var evaluation: MutableSet<ChairPaperEvaluation> = mutableSetOf(),

    @ManyToMany
    @JoinTable(name="PaperAuthor")
    var paperAuthors: MutableSet<Account> = mutableSetOf()
) : AbstractJpaHashable()