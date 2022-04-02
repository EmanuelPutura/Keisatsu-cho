package ubb.keisatsu.cms.repository

import ubb.keisatsu.cms.model.Account

interface AccountRepository {

    fun getAccounts(): Collection<Account>
}