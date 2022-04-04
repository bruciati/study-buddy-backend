package brc.studybuddy.backend.gateway.config

import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Configuration
class AuthConfig {
    @Value("\${secrets.key}")
    lateinit var secretKey: String

    @Bean
    fun privateKeyProvider(): Key = SecretKeySpec(
        Base64.getDecoder().decode(secretKey), SignatureAlgorithm.HS256.jcaName
    )
}
