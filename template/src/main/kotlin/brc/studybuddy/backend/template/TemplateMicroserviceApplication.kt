package brc.studybuddy.backend.template

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@SpringBootApplication
class TemplateMicroserviceApplication

fun main(args: Array<String>) {
    runApplication<TemplateMicroserviceApplication>(*args)
}
