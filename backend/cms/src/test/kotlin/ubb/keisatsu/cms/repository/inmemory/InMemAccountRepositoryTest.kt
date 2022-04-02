package ubb.keisatsu.cms.repository.inmemory

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class InMemAccountRepositoryTest {

    private val imRepo = InMemAccountRepository()

    @Test
    fun testAccounts() {


        val accounts = imRepo.getAccounts()

        Assertions.assertThat(accounts).size().isEqualTo(1)
        Assertions.assertThat(accounts).isNotEmpty
        Assertions.assertThat(accounts).allMatch { it.accountID >= 0 }
        Assertions.assertThat(accounts).allMatch { it.email.isNotEmpty() }
        Assertions.assertThat(accounts).allMatch { it.username.isNotEmpty() }
        Assertions.assertThat(accounts).allMatch { it.passwordDigest.isNotEmpty() }
        Assertions.assertThat(accounts).allMatch { it.firstName.isNotEmpty() }
        Assertions.assertThat(accounts).allMatch { it.lastName.isNotEmpty() }
        Assertions.assertThat(accounts).allMatch { it.address.isNotEmpty() }
        Assertions.assertThat(accounts).allMatch { it.birthDate.toString().isNotEmpty() }
    }

}