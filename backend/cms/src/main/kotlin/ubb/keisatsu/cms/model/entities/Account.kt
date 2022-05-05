package ubb.keisatsu.cms.model.entities

import ubb.keisatsu.cms.model.AbstractJpaHashable
import java.util.*
import javax.persistence.*

enum class UserRole {
    CHAIR, AUTHOR, REVIEWER
}

@Entity
@Table(name="Account")
class Account(
    @Column(name="Email", unique=true)
    var email: String,

    @Column(name="Username", unique=true)
    var userName: String,

    @Column(name="PasswordDigest")
    var password: String,

    @Column(name="Role")
    var role: UserRole,

    @Column(name="FirstName")
    var firstName: String? = null,

    @Column(name="LastName")
    var lastName: String? = null,

    @Column(name="Address")
    var address: String? = null,

    @Column(name="BirthDate")
    var birthDate: Date? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AccountId")
    var id: Int = -1

) : AbstractJpaHashable()