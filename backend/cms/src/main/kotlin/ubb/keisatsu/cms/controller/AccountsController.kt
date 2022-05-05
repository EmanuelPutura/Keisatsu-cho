package ubb.keisatsu.cms.controller

import org.springframework.web.bind.annotation.*
import ubb.keisatsu.cms.model.dto.AccountDto
import ubb.keisatsu.cms.model.entities.Account
import ubb.keisatsu.cms.model.entities.UserRole
import ubb.keisatsu.cms.service.AccountsService

@RestController
@CrossOrigin
class AccountsController(private var accountsService: AccountsService) {
    @PostMapping("accounts/sign-up")
    fun signUp(@RequestBody accountDto: AccountDto): Unit {
        /*
            TODO
            - create specialized classes (i.e., chair, author, reviewer), which inherit from Account
            - based on the userType value, create a corresponding type of user
            - add address and birth date fields
            - make Account an interface/abstract
            - hash passwords
            - server-side validation
        */

        val role = when (accountDto.userType) {
            "chair" -> UserRole.CHAIR
            "reviewer" -> UserRole.REVIEWER
            "author" -> UserRole.AUTHOR
            else -> null
        } ?: return

        accountsService.addAccount(Account(accountDto.email, accountDto.username, accountDto.password, role,
                accountDto.userFName, accountDto.userLName, accountDto.address, accountDto.birthDate))
    }

    @PostMapping("accounts/login")
    fun login(email: String, password: String): Boolean {
        val account: Account = accountsService.retrieveAccount(email) ?: return false
        return account.password == password
    }

    @GetMapping("accounts")
    fun getAccounts(): Iterable<Account> {
        return accountsService.retrieveAll()
    }
}
