package ubb.keisatsu.cms.model.entities

import javax.persistence.*

@Entity
class PaperConference {
    @Id
    @Column(name = "\"ConferenceID\"", nullable = false)
    var id: Int? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PaperID")
    var paperID: Paper? = null

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ConferenceID\"", nullable = false)
    var conference: Conference? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"AssigneeID\"")
    var assigneeID: Chair? = null

}