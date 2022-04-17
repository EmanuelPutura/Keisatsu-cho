package ubb.keisatsu.cms.model

data class SignUpAccountDTO (val address: String, val birthDate: String, val email: String, val password: String,
                        val userFName: String, val userLName: String, val username: String, val userType: String)

data class LoginAccountDTO(val email: String, val password: String)

data class Account(val email: String, val userName: String, val password: String)
