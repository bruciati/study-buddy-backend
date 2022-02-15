package brc.studybuddy.backend.users

import brc.studybuddy.backend.wrapper.WrapperConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UsersMicroserviceApplication : WrapperConfiguration()

fun main(args: Array<String>) {
    runApplication<UsersMicroserviceApplication>(*args)
}
