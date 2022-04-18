package ubb.keisatsu.cms.model

import org.ktorm.dsl.QueryRowSet
import org.ktorm.schema.BaseTable
import org.ktorm.schema.date
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import java.time.LocalDate


object AccountTable : BaseTable<Account>("Account") {
    val id = int("AccountID").primaryKey()
    val email = varchar("Email")
    val username = varchar("Username")
    val password = varchar("PasswordDigest")
    val firstName = varchar("FirstName")
    val lastName = varchar("LastName")
    val address = varchar("Address")
    val birthDate = date("BirthDate")

    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean) = Account(
        id = row[id],
        email = row[email].orEmpty(),
        username = row[username].orEmpty(),
        password = row[password].orEmpty(),
        firstName = row[firstName].orEmpty(),
        lastName = row[lastName].orEmpty(),
        address = row[address].orEmpty(),
        birthDate = row[birthDate],
        )
}

data class SignUpAccountDTO (val address: String, val birthDate: String, val email: String, val password: String,
                        val userFName: String, val userLName: String, val username: String, val userType: String)

data class LoginAccountDTO(val email: String, val password: String)

data class AccountDetailsDTO(val name: String, val type: String)

data class Account(
    var id: Int?, val email: String, val username: String, val password: String,
    val firstName: String, val lastName: String, val address: String, val birthDate: LocalDate?
)
