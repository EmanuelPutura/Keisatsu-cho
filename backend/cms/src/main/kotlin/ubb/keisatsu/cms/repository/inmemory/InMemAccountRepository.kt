package ubb.keisatsu.cms.repository.inmemory

import org.springframework.stereotype.Repository
import ubb.keisatsu.cms.model.Account
import ubb.keisatsu.cms.repository.AccountRepository
import java.time.LocalDate

@Repository
class InMemAccountRepository : AccountRepository {
    override fun getAccounts(): Collection<Account> {
        return listOf(Account(0, "das", "name", "stuff", "first", "last",
                "address", LocalDate.now()))

    }
}