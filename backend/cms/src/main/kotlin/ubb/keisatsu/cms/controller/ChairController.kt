package ubb.keisatsu.cms.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ubb.keisatsu.cms.model.dto.ConferenceDto
import ubb.keisatsu.cms.service.AccountsService
import ubb.keisatsu.cms.service.ConferencesService

@RestController
@CrossOrigin
class ChairController(private var conferencesService: ConferencesService, private var accountsService: AccountsService) {
    @PostMapping("conferences/add")
    fun addConference(@RequestBody conferenceDto: ConferenceDto) {
        println(accountsService.retrieveAll())
        val account = accountsService.retrieveAccount(conferenceDto.email)
        println(account)
    }
}