package ubb.keisatsu.cms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import ubb.keisatsu.cms.controller.Controller

@SpringBootApplication
class CmsApplication

fun main(args: Array<String>) {
    runApplication<CmsApplication>(*args)
}
