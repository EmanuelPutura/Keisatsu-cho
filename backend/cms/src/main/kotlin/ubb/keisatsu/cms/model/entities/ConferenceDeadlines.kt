package ubb.keisatsu.cms.model.entities

import ubb.keisatsu.cms.model.AbstractJpaHashable
import java.time.LocalDate
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
    var paperSubmissionDeadline: LocalDate,

    @Column(name="PaperReviewDeadline")
    var paperReviewDeadline: LocalDate,

    @Column(name="AcceptanceNotificationDeadline")
    var acceptanceNotificationDeadline: LocalDate,

    @Column(name="AcceptedPaperUploadDeadline")
    var acceptedPaperUploadDeadline: LocalDate
) : AbstractJpaHashable()