package ubb.keisatsu.cms.model.entities

import ubb.keisatsu.cms.model.AbstractJpaHashable
import javax.persistence.*

@Entity
@Table(name="TopicOfInterest")
class TopicOfInterest(
    @Column(name="Name", unique=true)
    var name: String,

    @ManyToMany(mappedBy = "topicsOfInterest")
    var conferencesForTopic: MutableSet<Conference> = mutableSetOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TopicID")
    var id: Int = -1
) : AbstractJpaHashable()
