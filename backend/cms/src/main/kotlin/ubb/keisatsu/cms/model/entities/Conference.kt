package ubb.keisatsu.cms.model.entities

import ubb.keisatsu.cms.model.AbstractJpaHashable
import javax.persistence.*

@Entity
@Table(name="Conference")
class Conference(
    @Column(name="Name")
    var name: String,

    @Column(name="Url")
    var url: String,

    @Column(name="Subtitles")
    var subtitles: String,

    @ManyToOne
    @JoinColumn(name = "MainOrganiserID", referencedColumnName = "AccountID")
    var mainOrganiser: Account,

    @ManyToMany
    @JoinTable(name = "ConferenceTopic")
    var topicsOfInterest: MutableSet<TopicOfInterest> = mutableSetOf(),

    @ManyToOne
    @JoinColumn(name = "DeadlinesID", referencedColumnName = "DeadlinesID")
    var conferenceDeadlines: ConferenceDeadlines? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ConferenceID")
    var id: Int = -1,

    @ManyToMany
    @JoinTable(name = "PaperConference")
    var assignedPapers: MutableSet<Paper> = mutableSetOf(),


) : AbstractJpaHashable()
