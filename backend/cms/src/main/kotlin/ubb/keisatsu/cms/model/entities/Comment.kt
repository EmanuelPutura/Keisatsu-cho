package ubb.keisatsu.cms.model.entities

import ubb.keisatsu.cms.model.AbstractJpaHashable
import javax.persistence.*

@Entity
@Table(name = "Comment")
class Comment (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"PaperID\"", nullable = false)
    var paper: Paper,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ReviewerID\"", nullable = false)
    var reviewer: Account,

    @Column(name="comment",length = 128)
    var comment: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CommentID")
    var id: Int = -1,
) : AbstractJpaHashable()