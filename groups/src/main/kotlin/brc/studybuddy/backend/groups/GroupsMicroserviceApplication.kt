package brc.studybuddy.backend.groups

import brc.studybuddy.backend.wrapper.WrapperConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GroupsMicroserviceApplication : WrapperConfiguration()

fun main(args: Array<String>) {
    runApplication<GroupsMicroserviceApplication>(*args)
}
