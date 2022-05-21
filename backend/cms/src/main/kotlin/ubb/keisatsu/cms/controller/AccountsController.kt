package ubb.keisatsu.cms.controller

import org.springframework.web.bind.annotation.*
import ubb.keisatsu.cms.model.dto.AccountIdDto
import ubb.keisatsu.cms.model.dto.AccountLoginCredentialsDto
import ubb.keisatsu.cms.model.dto.AccountRegisterDto
import ubb.keisatsu.cms.model.dto.AccountUserTypeDto
import ubb.keisatsu.cms.model.entities.Account
import ubb.keisatsu.cms.model.entities.UserRole
import ubb.keisatsu.cms.service.AccountsService

@RestController
@CrossOrigin
class AccountsController(private var accountsService: AccountsService) {
    @GetMapping("accounts/getUserData")
    fun getUserData(@RequestParam(name = "accountID") accountId: Int): AccountUserTypeDto? {
        val account: Account = accountsService.retrieveAccountByEmail(accountId) ?: return null
        val userRole = when (account.role) {
            UserRole.CHAIR -> "chair"
            UserRole.REVIEWER -> "reviewer"
            UserRole.AUTHOR -> "author"
            else -> null
        } ?: return null

        return AccountUserTypeDto(account.firstName, userRole)
    }

    @PostMapping("accounts/sign-up")
    fun signUp(@RequestBody accountDto: AccountRegisterDto): Unit {
        val role = when (accountDto.userType) {
            "chair" -> UserRole.CHAIR
            "reviewer" -> UserRole.REVIEWER
            "author" -> UserRole.AUTHOR
            else -> null
        } ?: return

        // the email and username of an account have to be unique
        if (accountsService.retrieveAccountByEmail(accountDto.email) != null || accountsService.retrieveAccountByUsername(accountDto.username) != null) {
            return
        }

        accountsService.addAccount(Account(accountDto.email, accountDto.username, accountDto.password, role,
                accountDto.userFName, accountDto.userLName, accountDto.address, accountDto.birthDate))
    }

    @PostMapping("accounts/login")
    fun login(@RequestBody accountLoginDto: AccountLoginCredentialsDto): AccountIdDto {
        val account: Account = accountsService.retrieveAccountByEmail(accountLoginDto.email) ?: return AccountIdDto(-1)
        return if (account.password == accountLoginDto.password) AccountIdDto(account.id) else AccountIdDto(-1)
    }
}
