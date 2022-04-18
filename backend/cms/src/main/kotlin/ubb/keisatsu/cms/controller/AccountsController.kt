package ubb.keisatsu.cms.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ubb.keisatsu.cms.model.Account
import ubb.keisatsu.cms.model.LoginAccountDTO
import ubb.keisatsu.cms.model.SignUpAccountDTO
import ubb.keisatsu.cms.service.AccountsService


@RestController
@CrossOrigin
class AccountsController(private val accountsService: AccountsService) {
    @PostMapping("accounts/sign-up")
    fun signUp(@RequestBody message: SignUpAccountDTO): Unit {
        /*
            TODO
            - create specialized classes (i.e., chair, author, reviewer), which inherit from Account
            - based on the userType value, create a corresponding type of user
            - add address and birth date fields
            - make Account an interface/abstract
            - hash passwords
            - server-side validation
        */

        accountsService.addAccount(Account(message.email, message.username, message.password))

        // TODO: remove line below
        println(accountsService.retrieveAll())
    }

    @PostMapping("accounts/login")
    fun login(@RequestBody message: LoginAccountDTO): Int {
        val account: Account = accountsService.retrieveAccount(message.email) ?: return -1;
        if (account.password != message.password) return -1;
        return accountsService.retrieveId(account.email) ?: -1;
    }
}
