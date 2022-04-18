package ubb.keisatsu.cms.model

import org.ktorm.dsl.QueryRowSet
import org.ktorm.schema.BaseTable
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import ubb.keisatsu.cms.model.ConferenceTable.primaryKey


object AccountTable : BaseTable<Account>("Account") {
    val id = int("AccountID").primaryKey()
    val email = varchar("Email")
    val username = varchar("Username")
    val password = varchar("Password")
    val firstName = varchar("FirstName")
    val lastName = varchar("LastName")
    val address = varchar("Address")
    val birthDat = varchar("BirthDate")

    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean) = Account(
   //     id = row[id] ?: 0,
        email = row[email].orEmpty(),
        username = row[username].orEmpty(),
        password = row[password].orEmpty(),
    )
}

data class SignUpAccountDTO (val address: String, val birthDate: String, val email: String, val password: String,
                        val userFName: String, val userLName: String, val username: String, val userType: String)

data class LoginAccountDTO(val email: String, val password: String)

data class Account(val email: String, val username: String, val password: String)
