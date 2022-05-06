package ubb.keisatsu.cms.controller

import org.springframework.web.bind.annotation.*
import ubb.keisatsu.cms.model.dto.ConferenceDto
import ubb.keisatsu.cms.model.entities.Conference
import ubb.keisatsu.cms.model.entities.UserRole
import ubb.keisatsu.cms.service.AccountsService
import ubb.keisatsu.cms.service.ConferencesService

@RestController
@CrossOrigin
class ChairController(private var conferencesService: ConferencesService, private var accountsService: AccountsService) {
    @GetMapping("conferences/get")
    fun getConferencesOrganizedBy(): String {
        return "Hello world!"
    }

    @PostMapping("conferences/add")
    fun addConference(@RequestBody conferenceDto: ConferenceDto) {
        val account = accountsService.retrieveAccount(conferenceDto.email)
        if (account == null || account.role != UserRole.CHAIR) {
            return
        }

        conferencesService.addConference(Conference(conferenceDto.name, conferenceDto.url, conferenceDto.subtitles, account))
    }
}
