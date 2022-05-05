package ubb.keisatsu.cms.model.entities

import ubb.keisatsu.cms.model.AbstractJpaHashable
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
class Chair(
    @Id
    @OneToOne
    @JoinColumn(name = "ChairID", referencedColumnName = "AccountID")
    var id: Account
) : AbstractJpaHashable(), Serializable
