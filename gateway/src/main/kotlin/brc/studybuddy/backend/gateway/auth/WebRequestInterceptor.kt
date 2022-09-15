package brc.studybuddy.backend.gateway.auth

import io.jsonwebtoken.JwtParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.graphql.server.WebGraphQlInterceptor
import org.springframework.graphql.server.WebGraphQlRequest
import org.springframework.graphql.server.WebGraphQlResponse
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

const val USERID_KEY = "X-UserID"

@Component
@Order(Int.MIN_VALUE + 1)
class WebRequestInterceptor : WebGraphQlInterceptor {
    @Autowired
    lateinit var jwtParser: JwtParser

    val logger: Logger by lazy { LoggerFactory.getLogger(WebRequestInterceptor::class.java) }

    override fun intercept(request: WebGraphQlRequest, chain: WebGraphQlInterceptor.Chain): Mono<WebGraphQlResponse> {
        logger.info("${this::class.java} - DEBUG#1", request.uri.toString())
        logger.info("${this::class.java} - DEBUG#2", request.headers.toString())
        logger.info("${this::class.java} - DEBUG#3", request.document)
        return chain.next(request)
    }
}
