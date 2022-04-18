package ubb.keisatsu.cms.service

import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.Account
import ubb.keisatsu.cms.repository.database.DbAccountsRepository

@Service
class AccountsService(private val accountsRepository: DbAccountsRepository) {
    init {
//        loadDefaultAccounts()
    }

    private fun loadDefaultAccounts() : Unit {
        addAccount(Account("gigibecali@gmail.com", "gigibecali", "gigi123"))
        addAccount(Account("marian.popescu@yahoo.com", "marian2001", "032001"))
        addAccount(Account("cristina.pop@gmail.com", "cristina15", "popcris"))
    }

    fun addAccount(account: Account): Unit = accountsRepository.add(account)

    fun retrieveAccount(email: String): Account? = accountsRepository.retrieveAccountByEmail(email)

    fun retrieveAll(): Collection<Account> = accountsRepository.retrieveAll()

    fun retrieveId(email: String): Int? = accountsRepository.retrieveId(email)
}
