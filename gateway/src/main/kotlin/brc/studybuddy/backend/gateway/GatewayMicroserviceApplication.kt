package brc.studybuddy.backend.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.http.HttpStatus

@EnableEurekaClient
@SpringBootApplication
class GatewayMicroserviceApplication

fun main(args: Array<String>) {
    runApplication<GatewayMicroserviceApplication>(*args)
}
