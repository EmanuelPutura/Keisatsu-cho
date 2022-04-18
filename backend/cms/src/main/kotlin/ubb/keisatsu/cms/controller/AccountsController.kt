package ubb.keisatsu.cms.controller

import com.google.gson.Gson
import org.springframework.web.bind.annotation.*
import ubb.keisatsu.cms.model.Account
import ubb.keisatsu.cms.model.AccountDetailsDTO
import ubb.keisatsu.cms.model.LoginAccountDTO
import ubb.keisatsu.cms.model.SignUpAccountDTO
import ubb.keisatsu.cms.service.AccountsService
import java.time.LocalDate
import java.time.format.DateTimeFormatter


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

        val date = LocalDate.parse(message.birthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        accountsService.addAccount(Account(null, message.email, message.username, message.password, message.userFName, message.userLName, message.address, date), message.userType)
    }

    @PostMapping("accounts/login")
    fun login(@RequestBody message: LoginAccountDTO): Int {
        val account: Account = accountsService.retrieveAccount(message.email) ?: return -1;
        if (account.password != message.password) return -1;
        return accountsService.retrieveId(account.email) ?: -1;
    }

    @GetMapping("accounts/details")
    fun getUserDetails(@RequestParam accountID: Int): String? {
        val account = accountsService.retrieveAccount(accountID) ?: return null
        val chair = accountsService.retrieveChair(accountID)
        val accountDto = AccountDetailsDTO(account.firstName, "chair")
        return Gson().toJson(accountDto)
    }
}
