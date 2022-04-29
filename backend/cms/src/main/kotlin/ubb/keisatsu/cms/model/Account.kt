package ubb.keisatsu.cms.model

import javax.persistence.*

@Entity
class Account(

    @Column(unique=true)
    var email: String,

    @Column(unique=true)
    var userName: String,

    @Column(name="PasswordDigest")
    var password: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AccountId")
    var id: Int = -1

) : AbstractJpaHashable()