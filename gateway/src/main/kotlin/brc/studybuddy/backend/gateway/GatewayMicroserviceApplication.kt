package brc.studybuddy.backend.gateway

import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec

@EnableEurekaClient
@SpringBootApplication
class GatewayMicroserviceApplication {

    @Value("\${secrets.key}")
    lateinit var secretKey: String

    @Bean
    fun privateKeyProvider(): Key = SecretKeySpec(
        Base64.getDecoder().decode(secretKey), SignatureAlgorithm.HS256.jcaName
    )
}

fun main(args: Array<String>) {
    runApplication<GatewayMicroserviceApplication>(*args)
}
