package brc.studybuddy.backend.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GatewayMicroserviceApplication

fun main(args: Array<String>) {
    runApplication<GatewayMicroserviceApplication>(*args)
}
