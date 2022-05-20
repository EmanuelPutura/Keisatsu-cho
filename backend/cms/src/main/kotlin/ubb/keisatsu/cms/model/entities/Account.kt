package ubb.keisatsu.cms.model.entities

import ubb.keisatsu.cms.model.AbstractJpaHashable
import java.time.LocalDate
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
    var firstName: String,

    @Column(name="LastName")
    var lastName: String,

    @Column(name="Address")
    var address: String,

    @Column(name="BirthDate")
    var birthDate: LocalDate,

    @ManyToMany
    @JoinTable(name = "AccountTopic")
    var topicsOfInterest: MutableSet<TopicOfInterest> = mutableSetOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AccountId")
    var id: Int = -1,

    @OneToMany(mappedBy = "accountID")
    var evaluation: MutableSet<ChairPaperEvaluation> = mutableSetOf(),

    @ManyToMany(mappedBy = "paperAuthors")
    var papersForAuthor: MutableSet<Paper> = mutableSetOf()

) : AbstractJpaHashable()
