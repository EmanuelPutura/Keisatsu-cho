package ubb.keisatsu.cms.model.entities

import ubb.keisatsu.cms.model.AbstractJpaHashable
import java.util.*
import javax.persistence.*

@Entity
@Table(name="ConferenceDeadlines")
class ConferenceDeadlines(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DeadlinesID")
    var id: Int,

    @Column(name="PaperSubmissionDeadline")
    var paperSubmissionDeadline: Date,

    @Column(name="PaperReviewDeadline")
    var paperReviewDeadline: Date,

    @Column(name="AcceptanceNotificationDeadline")
    var acceptanceNotificationDeadline: Date,

    @Column(name="AcceptedPaperUploadDeadline")
    var acceptedPaperUploadDeadline: Date
) : AbstractJpaHashable()