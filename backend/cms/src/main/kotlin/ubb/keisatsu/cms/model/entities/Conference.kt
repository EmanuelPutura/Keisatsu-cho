package ubb.keisatsu.cms.model.entities

import ubb.keisatsu.cms.model.AbstractJpaHashable
import javax.persistence.*

@Entity
class Conference(
    @Column(name="Name")
    var name: String,

    @Column
    var url: String,

    @ManyToOne
    @JoinColumn(name = "MainOrganiserID", referencedColumnName = "ChairID")
    var mainOrganiser: Chair,

    @ManyToOne
    @JoinColumn(name = "DeadlinesID", referencedColumnName = "DeadlinesID")
    var conferenceDeadlines: ConferenceDeadlines,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ConferenceID")
    var id: Int = -1
) : AbstractJpaHashable()
