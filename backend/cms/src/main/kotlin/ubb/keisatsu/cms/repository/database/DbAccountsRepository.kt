package ubb.keisatsu.cms.repository.database

import org.ktorm.database.Database
import org.ktorm.dsl.*
import ubb.keisatsu.cms.model.Account
import ubb.keisatsu.cms.model.AccountTable
import ubb.keisatsu.cms.model.Conference
import ubb.keisatsu.cms.model.ConferenceTable
import ubb.keisatsu.cms.repository.memory.MemoryRepository


@org.springframework.stereotype.Repository
class DbAccountsRepository(val dbConfig: DatabaseConfig) : MemoryRepository<Account>() {
    private lateinit var database: Database

    init {
        connect()
    }

    private fun connect(): Unit {
        database = Database.connect(url = dbConfig.url, user = dbConfig.user, password = dbConfig.password)
        for (row in database.from(AccountTable).select()){
            println(row[AccountTable.username])
        }
    }

    override fun add(entity: Account) {

        val usedEmail = database
            .from(AccountTable)
            .select()
            .where{ (AccountTable.email eq entity.email) }
            .iterator()
            .hasNext()

        if(usedEmail) {
            throw RuntimeException("Email is already in use")
        }

        val usedUsername = database
            .from(AccountTable)
            .select()
            .where{ (AccountTable.username eq entity.username) }
            .iterator()
            .hasNext()

        if(usedUsername) {
            throw RuntimeException("Username is already in use")
        }

        database.insert(AccountTable) {
            set(it.username, entity.username)
            set(it.email, entity.email)
            set(it.password, entity.password)
        }
    }

    override fun update(entity: Account) {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Account) {
        TODO("Not yet implemented")
    }

    override fun retrieveAll(): Collection<Account> {
        return database
            .from(AccountTable)
            .select()
            .map { AccountTable.createEntity(it) }
    }

    fun retrieveAccountByEmail(email: String): Account? {
        return database
            .from(AccountTable)
            .select()
            .where(AccountTable.email eq email)
            .map { AccountTable.createEntity(it) }
            .firstOrNull()
    }

    fun retrieveAccountByUsername(username: String): Account? {
        return database
            .from(AccountTable)
            .select()
            .where(AccountTable.username eq username)
            .map { AccountTable.createEntity(it) }
            .firstOrNull()
    }

    fun retrieveId(email: String): Int? {
        return database
            .from(AccountTable)
            .select()
            .where(AccountTable.email eq email)
            .map { it[AccountTable.id] }
            .firstOrNull()
    }
}