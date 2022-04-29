package ubb.keisatsu.cms.service

import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.Account
import ubb.keisatsu.cms.repository.AccountsRepository

@Service
class AccountsService(private val accountsRepository: AccountsRepository) {
    init {
  //      loadDefaultAccounts()
    }

    private fun loadDefaultAccounts() : Unit {
        addAccount(Account("gigibecali@gmail.com", "gigibecali", "gigi123"))
        addAccount(Account("marian.popescu@yahoo.com", "marian2001", "032001"))
        addAccount(Account("cristina.pop@gmail.com", "cristina15", "popcris"))
    }

    fun addAccount(account: Account): Account = accountsRepository.save(account)

    fun retrieveAccount(email: String): Account? = accountsRepository.findByEmail(email)

    fun retrieveAll(): Iterable<Account> = accountsRepository.findAll()
}
