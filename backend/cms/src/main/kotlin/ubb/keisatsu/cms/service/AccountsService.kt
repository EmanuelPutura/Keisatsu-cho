package ubb.keisatsu.cms.service

import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.Account
import ubb.keisatsu.cms.model.Chair
import ubb.keisatsu.cms.repository.database.DbAccountsRepository
import ubb.keisatsu.cms.repository.database.DbChairsRepository

@Service
class AccountsService(private val accountsRepository: DbAccountsRepository, private val chairsRepository: DbChairsRepository) {
    fun addAccount(account: Account, userType: String): Unit {
        accountsRepository.add(account)
        val id = accountsRepository.retrieveId(account.email)
        chairsRepository.add(Chair(id!!))
    }

    fun retrieveAccount(email: String): Account? = accountsRepository.retrieveAccountByEmail(email)

    fun retrieveAll(): Collection<Account> = accountsRepository.retrieveAll()

    fun retrieveId(email: String): Int? = accountsRepository.retrieveId(email)

    fun retrieveAccount(id: Int): Account? {
        val accounts = accountsRepository.retrieveAll()
        val filterResult = accounts.filter { it.id == id }

        if (filterResult.isEmpty())
            return null
        return filterResult[0]
    }

    fun retrieveChair(id: Int): Chair? {
        val chairs = chairsRepository.retrieveAll()
        val filterResult = chairs.filter { it.id == id }

        if (filterResult.isEmpty())
            return null
        return filterResult[0]
    }
}
