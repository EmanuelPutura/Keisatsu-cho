package ubb.keisatsu.cms.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ubb.keisatsu.cms.model.Conference
import ubb.keisatsu.cms.model.ConferenceDTO
import ubb.keisatsu.cms.service.ChairService

@RestController
@CrossOrigin
class ChairController(private val conferencesService: ChairService) {
    @PostMapping("conferences/add")
    fun addConference(@RequestBody message: ConferenceDTO): Unit {
        // TODO: modify organizer and deadlines ID
        conferencesService.addConference(Conference(message.name, message.url, 1, 1))
    }
}