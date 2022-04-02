package ubb.keisatsu.cms.repository.memory

import org.springframework.stereotype.Repository
import ubb.keisatsu.cms.model.Account
import ubb.keisatsu.cms.repository.AccountsRepository
import java.util.*
import kotlin.collections.ArrayList

@Repository
class MemoryAccountsRepository: AccountsRepository {
    private var accounts: MutableList<Account> = mutableListOf()

    override fun addAccount(account: Account) {
        // TODO: check for the account not to already be present in the repository
        if (retrieveAccount(account.email) != null)
            return

        accounts.add(account)
    }

    override fun retrieveAccount(email: String): Account? {
        return accounts.find { account -> account.email == email }
    }

    override fun retrieveAll(): Collection<Account> {
        return accounts
    }
}
