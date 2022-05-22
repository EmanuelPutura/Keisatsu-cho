package ubb.keisatsu.cms.model.dto

import org.springframework.web.multipart.MultipartFile

data class PaperEvaluationDto(val chairID: Int, val paperID: Int, val conferenceID: Int, val response: Boolean)

data class PaperDetailsDto(val id: Int, val title: String, val abstract: String, val authors: Collection<AccountUserDataDto>,
                           val keywords: String, val topic: String, val conferenceID: Int)

data class PaperFromAuthorDto(val id: Int, val title: String, val abstract: String, val authors: Collection<AccountUserDataDto>,
                              val keywords: String, val topic: String, val conferenceName: String)

data class SubmittedPaperDetailsDto(val token: Int, val title: String, val abstract: String, val authors: Collection<AccountUserDataDto>,
                                    val keywords: String, val interestTopic: String, val conference: Int)

data class UploadFullPaperDto(val token: Int, val paper: Int, val file: MultipartFile)

data class PaperSentenceDto(val token: Int, val paperID: Int)
