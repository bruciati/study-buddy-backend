package brc.studybuddy.backend.template

import brc.studybuddy.backend.wrapper.WrapperConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MicroserviceApplication : WrapperConfiguration()

fun main(args: Array<String>) {
    runApplication<MicroserviceApplication>(*args)
}
