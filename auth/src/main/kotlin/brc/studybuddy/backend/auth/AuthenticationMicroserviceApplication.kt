package brc.studybuddy.backend.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthenticationMicroserviceApplication

fun main(args: Array<String>) {
    runApplication<AuthenticationMicroserviceApplication>(*args)
}
