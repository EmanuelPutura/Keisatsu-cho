package ubb.keisatsu.cms.model.entities

import ubb.keisatsu.cms.model.AbstractJpaHashable
import java.io.Serializable
import javax.persistence.*

@Embeddable
class ChairPaperKey(
        @Column(name="PaperID")
        val paperID: Int=-1,
        @Column(name="AccountID")
        val accountID: Int=-1
) : Serializable

@Entity
@Table(name="ChairPaperEvaluation")
class ChairPaperEvaluation (
    @EmbeddedId
    var id: ChairPaperKey,


    @ManyToOne
    @MapsId("PaperID")
    @JoinColumn(name = "\"PaperID\"", nullable = false)
    var paper: Paper? = null,


    @ManyToOne
    @MapsId("AccountID")
    @JoinColumn(name = "\"AccountID\"")
    var accountID: Account? = null,

    @Column(name = "\"IsAccepted\"")
    var isAccepted: Boolean=false
) : AbstractJpaHashable()