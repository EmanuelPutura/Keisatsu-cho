package ubb.keisatsu.cms.repository

import org.springframework.data.repository.CrudRepository
import ubb.keisatsu.cms.model.entities.Account

interface AccountsRepository : CrudRepository<Account, Int> {
    fun findByEmail(email : String): Account?

    fun findByUserName(username : String): Account?
}
