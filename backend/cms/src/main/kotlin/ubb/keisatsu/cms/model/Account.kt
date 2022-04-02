package ubb.keisatsu.cms.model

import java.time.LocalDate


data class Account(val accountID: Int, val email: String, val username: String,
                   val passwordDigest: String, val firstName: String, val lastName: String,
                   val address: String, val birthDate: LocalDate)