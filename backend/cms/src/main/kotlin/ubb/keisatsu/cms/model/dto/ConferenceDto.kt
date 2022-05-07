package ubb.keisatsu.cms.model.dto

data class ConferenceSubmitDto(val email: String, val name: String, val subtitles: String, val url: String)

data class ConferenceDetailsDto(val id: Int, val name: String, val url: String, val subtitles: String, val topics: String,
                                val submission: String?, val review: String?, val acceptance: String?, val upload: String?)
