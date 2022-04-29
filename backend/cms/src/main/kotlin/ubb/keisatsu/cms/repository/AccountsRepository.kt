package ubb.keisatsu.cms.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ubb.keisatsu.cms.model.Account

interface AccountsRepository : CrudRepository<Account, Int> {
    fun findByEmail(email : String): Account?

    fun findByUserName(username : String): Account?
}
