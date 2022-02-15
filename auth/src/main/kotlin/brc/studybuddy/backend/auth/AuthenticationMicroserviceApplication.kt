package brc.studybuddy.backend.auth

import brc.studybuddy.backend.wrapper.ResponseWrapper
import brc.studybuddy.backend.wrapper.WebExceptionWrapper
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.runApplication
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.reactive.accept.RequestedContentTypeResolver
import org.springframework.web.reactive.result.view.ViewResolver
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec

@SpringBootApplication
@Configuration
class AuthenticationMicroserviceApplication {

    @Value("\${secret.key}")
    lateinit var secretKey: String

    @Autowired
    lateinit var serverCodecConfigurer: ServerCodecConfigurer

    @Autowired
    lateinit var requestedContentTypeResolver: RequestedContentTypeResolver

    @Autowired
    lateinit var viewResolvers: ObjectProvider<ViewResolver>

    @Autowired
    lateinit var errorAttributes: ErrorAttributes

    @Autowired
    lateinit var applicationContext: ApplicationContext

    val resources: WebProperties.Resources = WebProperties.Resources()

    @Bean
    fun responseWrapper(): ResponseWrapper = ResponseWrapper(
        serverCodecConfigurer.writers, requestedContentTypeResolver
    )

    @Bean
    @Order(-2)
    fun webExceptionWrapper(): WebExceptionWrapper = WebExceptionWrapper(
        viewResolvers, serverCodecConfigurer, errorAttributes, resources, applicationContext
    )

    @Bean
    fun privateKeyProvider(): Key {
        return SecretKeySpec(
            Base64.getDecoder().decode(secretKey),
            SignatureAlgorithm.HS256.jcaName
        )
    }

}

fun main(args: Array<String>) {
    runApplication<AuthenticationMicroserviceApplication>(*args)
}
