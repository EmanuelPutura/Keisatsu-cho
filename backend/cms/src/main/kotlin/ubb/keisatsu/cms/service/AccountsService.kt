package ubb.keisatsu.cms.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.dto.AccountUserDataDto
import ubb.keisatsu.cms.model.entities.Account
import ubb.keisatsu.cms.model.entities.UserRole
import ubb.keisatsu.cms.repository.AccountsRepository
import java.time.LocalDate

@Service
class AccountsService(private val accountsRepository: AccountsRepository) {
    init {
//        addDefaultAccounts()
    }

    private final fun addDefaultAccounts() {
        accountsRepository.save(Account("chair1@chair.com", "Chair1", "Chair123", UserRole.CHAIR,
            "ChairF1", "ChairL1", "C1Address", LocalDate.now()))
        accountsRepository.save(Account("chair2@chair.com", "Chair2", "Chair123", UserRole.CHAIR,
            "ChairF2", "ChairL2", "C2Address", LocalDate.now()))
        accountsRepository.save(Account("reviewer1@reviewer.com", "Reviewer1", "Reviewer123", UserRole.REVIEWER,
            "ReviewerF1", "ReviewerL1", "R1Address", LocalDate.now()))
        accountsRepository.save(Account("author1@author.com", "Author1", "Author123", UserRole.AUTHOR,
            "AuthorF1", "AuthorL1", "A1Address", LocalDate.now()))
    }

    fun addAccount(account: Account): Account = accountsRepository.save(account)

    fun retrieveAccount(id: Int): Account? = accountsRepository.findByIdOrNull(id)

    fun retrieveAccountByEmail(email: String): Account? = accountsRepository.findByEmail(email)

    fun retrieveAccountByUsername(username: String): Account? = accountsRepository.findByUserName(username)

    fun retrieveAll(): Iterable<Account> = accountsRepository.findAll()

    fun retrieveReviewers(): Iterable<Account> = accountsRepository.findByRole(UserRole.REVIEWER)

    fun convertToAccountUserDataDtos(accounts: Collection<Account>): Collection<AccountUserDataDto> {
        val accountDtos: MutableCollection<AccountUserDataDto> = mutableListOf()
        accounts.forEach{ account ->
            accountDtos.add(AccountUserDataDto("${account.firstName} ${account.lastName}", account.email, account.address))
        }

        return accountDtos;
    }
}
