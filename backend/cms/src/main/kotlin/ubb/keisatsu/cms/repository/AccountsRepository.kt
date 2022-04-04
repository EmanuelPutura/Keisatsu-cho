package ubb.keisatsu.cms.repository

import org.springframework.stereotype.Repository
import ubb.keisatsu.cms.model.Account

interface AccountsRepository {
    fun addAccount(account: Account): Unit

    fun retrieveAccount(email : String): Account?

    fun retrieveAll(): Collection<Account>
}
