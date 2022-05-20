package ubb.keisatsu.cms.model.dto

data class PaperEvaluationDto(val chairID: Int, val paperID: Int, val conferenceID: Int, val response: Boolean)

data class PaperDetailsDto(val id: Int, val title: String, val abstract: String, val authors: Collection<AccountUserDataDto>,
                           val keywords: String, val topic: String, val conferenceID: Int)

data class PaperFromAuthorDto(val id: Int, val title: String, val abstract: String, val authors: Collection<AccountUserDataDto>,
                              val keywords: String, val topic: String, val decided: Boolean)
