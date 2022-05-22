package ubb.keisatsu.cms.controller

import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import ubb.keisatsu.cms.model.dto.*
import ubb.keisatsu.cms.model.entities.Account
import ubb.keisatsu.cms.model.entities.UserRole
import ubb.keisatsu.cms.security.JwtTokenUtil
import ubb.keisatsu.cms.service.AccountsService
import java.time.LocalDate

@RestController
@CrossOrigin
class AccountsController(
    private var accountsService: AccountsService,
    private var authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil,
    private val passwordEncoder: PasswordEncoder,
    ) {

    private val log = LoggerFactory.logger(AccountsController::class.java)
    private val MIN_USER_AGE = 12L  // minimum user age

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
        log.info("Sign up request")
        val role = when (accountDto.userType) {
            "chair" -> UserRole.CHAIR
            "reviewer" -> UserRole.REVIEWER
            "author" -> UserRole.AUTHOR
            else -> null
        } ?: return ErrorDto(false, "The account type received via post request is not valid!")

        // the email and username of an account have to be unique
        if (accountsService.retrieveAccountByEmail(accountDto.email) != null || accountsService.retrieveAccountByUsername(accountDto.username) != null) {
            log.error("Email and username must be unique")
            return ErrorDto(false, "The email and username of an account must be unique!")
        }

        // a user must be older than MIN_USER_AGE years old
        if (!accountDto.birthDate.isBefore(LocalDate.now().minusYears(MIN_USER_AGE))) {
            log.error("User too young")
            return ErrorDto(false, "Invalid user age: user should be older than $MIN_USER_AGE years old!")
        }

        accountsService.addAccount(Account(accountDto.email, accountDto.username, passwordEncoder.encode(accountDto.password), role,
                accountDto.userFName, accountDto.userLName, accountDto.address, accountDto.birthDate))

        return ErrorDto(true)
    }

    @PostMapping("accounts/login")
    fun login(@RequestBody accountLoginDto: AccountLoginCredentialsDto): ResponseEntity<AccountIdDto> {
        log.info("Login request")
        try {
            val authenticate: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(accountLoginDto.email, accountLoginDto.password))

            val account: Account = authenticate.principal as Account
            //autaccountsService.validate(accountLoginDto.email, accountLoginDto.password)

            return ResponseEntity.ok()
                .header(
                    HttpHeaders.AUTHORIZATION,
                    jwtTokenUtil.createJWT(account.id.toString(), null, account.email, account.role, 60 * 60)
                )
                .body(AccountIdDto(account.id))
        } catch(e: Exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }
}
