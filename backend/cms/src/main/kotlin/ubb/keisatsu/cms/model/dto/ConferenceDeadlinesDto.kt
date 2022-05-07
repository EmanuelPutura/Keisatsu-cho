package ubb.keisatsu.cms.model.dto

import java.time.LocalDate

data class ConferenceDeadlinesDto(val conferenceID: Int, val submission: LocalDate, val review: LocalDate, val acceptance: LocalDate, val upload: LocalDate)
