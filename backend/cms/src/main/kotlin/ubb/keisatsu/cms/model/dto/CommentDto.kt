package ubb.keisatsu.cms.model.dto

data class CommentDto(val name: String, val comment: String)

data class CommentSubmitDto(val comment: String, val paperID: Int, val token: Int)