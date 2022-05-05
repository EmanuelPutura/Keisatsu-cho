package ubb.keisatsu.cms.service

import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.entities.Account
import ubb.keisatsu.cms.repository.AccountsRepository

@Service
class AccountsService(private val accountsRepository: AccountsRepository) {
    fun addAccount(account: Account): Account = accountsRepository.save(account)

    fun retrieveAccount(email: String): Account? = accountsRepository.findByEmail(email)

    fun retrieveAll(): Iterable<Account> = accountsRepository.findAll()
}
