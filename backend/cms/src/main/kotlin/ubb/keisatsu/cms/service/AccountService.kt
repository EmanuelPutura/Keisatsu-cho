package ubb.keisatsu.cms.service

import org.springframework.stereotype.Service
import ubb.keisatsu.cms.repository.AccountRepository


@Service
class AccountService(private val repo:AccountRepository) {
}