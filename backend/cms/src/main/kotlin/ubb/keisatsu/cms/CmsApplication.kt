package ubb.keisatsu.cms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CmsApplication

fun main(args: Array<String>) {
//    val o1 = DbConferencesRepository()
//    o1.add(Conference("TestConference2", "http://testwebsite.com/", 1, 1))

    runApplication<CmsApplication>(*args)
}
