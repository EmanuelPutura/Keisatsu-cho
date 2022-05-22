package ubb.keisatsu.cms.model.entities

import org.springframework.data.util.ProxyUtils
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ubb.keisatsu.cms.model.AbstractJpaHashable
import java.time.LocalDate
import javax.persistence.*

enum class UserRole : GrantedAuthority {
    CHAIR, AUTHOR, REVIEWER;

    override fun getAuthority(): String {
        return when(this) {
            CHAIR -> "CHAIR"
            AUTHOR -> "AUTHOR"
            REVIEWER -> "REVIEWER"
        }
    }
}

@Entity
@Table(name="Account")
class Account(
    @Column(name="Email", unique=true)
    var email: String,

    @Column(name="Username", unique=true)
    var userName: String,

    @Column(name="PasswordDigest")
    private var password: String,

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

    @ManyToMany(mappedBy = "paperAuthors")
    var papersForAuthor: MutableSet<Paper> = mutableSetOf()



) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf<GrantedAuthority>(role)
    }

    override fun getPassword(): String {
        return password
    }


    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false
        other as Account
        return this.email == other.email
    }

    override fun hashCode(): Int {
        return email.hashCode()
    }
}
