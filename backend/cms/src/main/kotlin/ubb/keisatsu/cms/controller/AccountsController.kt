package ubb.keisatsu.cms.controller

import org.springframework.web.bind.annotation.*
import ubb.keisatsu.cms.model.dto.*
import ubb.keisatsu.cms.model.entities.Account
import ubb.keisatsu.cms.model.entities.UserRole
import ubb.keisatsu.cms.service.AccountsService

@RestController
@CrossOrigin
class AccountsController(private var accountsService: AccountsService) {
    @GetMapping("accounts/getUserData")
    fun getUserData(@RequestParam(name = "accountID") accountId: Int): AccountUserTypeDto? {
        val account: Account = accountsService.retrieveAccount(accountId) ?: return null
        val userRole = when (account.role) {
            UserRole.CHAIR -> "chair"
            UserRole.REVIEWER -> "reviewer"
            UserRole.AUTHOR -> "author"
            else -> null
        } ?: return null

        return AccountUserTypeDto(account.firstName, userRole)
    }

    @PostMapping("accounts/sign-up")
    fun signUp(@RequestBody accountDto: AccountRegisterDto): ErrorDto {
        val role = when (accountDto.userType) {
            "chair" -> UserRole.CHAIR
            "reviewer" -> UserRole.REVIEWER
            "author" -> UserRole.AUTHOR
            else -> null
        } ?: return ErrorDto(false, "The account type received via post request is not valid!")

        // the email and username of an account have to be unique
        if (accountsService.retrieveAccountByEmail(accountDto.email) != null || accountsService.retrieveAccountByUsername(accountDto.username) != null) {
            return ErrorDto(false, "The email and username of an account must be unique!")
        }

        accountsService.addAccount(Account(accountDto.email, accountDto.username, accountDto.password, role,
                accountDto.userFName, accountDto.userLName, accountDto.address, accountDto.birthDate))

        return ErrorDto(true)
    }

    @PostMapping("accounts/login")
    fun login(@RequestBody accountLoginDto: AccountLoginCredentialsDto): AccountIdDto {
        val account: Account = accountsService.retrieveAccountByEmail(accountLoginDto.email) ?: return AccountIdDto(-1)
        return if (account.password == accountLoginDto.password) AccountIdDto(account.id) else AccountIdDto(-1)
    }
}
