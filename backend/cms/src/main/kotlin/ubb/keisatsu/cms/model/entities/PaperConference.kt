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

    //TODO Reverse Engineering! Migrate other columns to the entity
}