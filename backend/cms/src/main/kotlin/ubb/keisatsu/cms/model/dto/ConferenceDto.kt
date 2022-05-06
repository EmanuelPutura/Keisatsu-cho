package ubb.keisatsu.cms.model.dto

import java.util.*

data class ConferenceSubmitDto(val email: String, val name: String, val subtitles: String, val url: String)

data class ConferenceDetailsDto(val id: Int, val name: String, val url: String, val subtitles: String, val topics: String,
                                val submission: Date?, val review: Date?, val acceptance: Date?, val upload: Date?)
