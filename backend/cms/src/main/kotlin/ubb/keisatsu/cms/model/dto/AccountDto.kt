package ubb.keisatsu.cms.model.dto

import java.time.LocalDate

data class AccountLoginCredentialsDto(val email: String, val password: String)

data class AccountRegisterDto(val email: String, val userType: String, val username: String, val password: String,
                              val userFName: String, val userLName: String, val address: String, val birthDate: LocalDate
)

data class AccountIdDto(val id: Int)

data class AccountUserTypeDto(val name: String, val type: String)

data class AccountUserDataDto(val name: String, val email: String, val address: String)
