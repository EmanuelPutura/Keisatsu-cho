package ubb.keisatsu.cms.model.entities

import javax.persistence.*

@Entity
class ChairPaperEvaluation {
    @Id
    @Column(name = "\"PaperID\"", nullable = false)
    var id: Int? = null

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PaperID")
    var paper: Paper? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ChairID\"")
    var chairID: Chair? = null

    @Column(name = "\"IsAccepted\"")
    var isAccepted: Boolean? = null

}