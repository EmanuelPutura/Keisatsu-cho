package ubb.keisatsu.cms.model.dto

import java.util.*

data class AccountDto(val email: String, val password: String)

data class AccountRegisterDto(val email: String, val userType: String, val username: String, val password: String,
                              val userFName: String, val userLName: String, val address: String, val birthDate: Date)

data class AccountIdDto(val id: Int)
