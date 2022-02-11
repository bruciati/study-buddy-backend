package brc.studybuddy.backend.template

import brc.studybuddy.backend.template.wrapper.ResponseWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.reactive.accept.RequestedContentTypeResolver

@SpringBootApplication
class MicroserviceApplication {
    @Autowired
    lateinit var serverCodecConfigurer: ServerCodecConfigurer

    @Autowired
    lateinit var requestedContentTypeResolver: RequestedContentTypeResolver

    @Bean
    fun responseWrapper(): ResponseWrapper = ResponseWrapper(
        serverCodecConfigurer.writers, requestedContentTypeResolver
    )

    @Bean
    fun resources(): WebProperties.Resources {
        return WebProperties.Resources()
    }
}

fun main(args: Array<String>) {
    runApplication<MicroserviceApplication>(*args)
}
