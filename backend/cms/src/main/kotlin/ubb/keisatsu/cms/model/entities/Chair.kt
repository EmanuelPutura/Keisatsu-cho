package ubb.keisatsu.cms.model.entities

import javax.persistence.*

@Entity
class Chair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ChairID\"", nullable = false)
    var id: Int? = null

    //TODO Reverse Engineering! Migrate other columns to the entity
}