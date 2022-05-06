package ubb.keisatsu.cms.model.dto

import java.util.*

data class AccountLoginCredentialsDto(val email: String, val password: String)

data class AccountRegisterDto(val email: String, val userType: String, val username: String, val password: String,
                              val userFName: String, val userLName: String, val address: String, val birthDate: Date)

data class AccountIdDto(val id: Int)

data class AccountUserDataDto(val name: String, val type: String)
