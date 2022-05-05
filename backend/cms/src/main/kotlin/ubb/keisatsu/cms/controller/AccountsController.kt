package ubb.keisatsu.cms.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import ubb.keisatsu.cms.model.entities.Account
import ubb.keisatsu.cms.service.AccountsService

@RestController
@CrossOrigin
class AccountsController(private var accountsService: AccountsService) {
    @PostMapping("accounts/sign-up")
    fun signUp(email: String, userType: String, userName: String, password: String, passwordRepeat: String): Unit {
        /*
            TODO
            - create specialized classes (i.e., chair, author, reviewer), which inherit from Account
            - based on the userType value, create a corresponding type of user
            - add address and birth date fields
            - make Account an interface/abstract
            - hash passwords
            - server-side validation
        */
        accountsService.addAccount(Account(email, userName, password))

        // TODO: remove line below
        println(accountsService.retrieveAll())
    }

    @PostMapping("accounts/login")
    fun login(email: String, password: String): Boolean {
        val account: Account = accountsService.retrieveAccount(email) ?: return false
        return account.password == password
    }
}
