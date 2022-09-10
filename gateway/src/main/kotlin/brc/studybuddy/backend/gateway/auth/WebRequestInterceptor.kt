package brc.studybuddy.backend.gateway.auth

import io.jsonwebtoken.JwtException
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

    val logger: Logger by lazy { LoggerFactory.getLogger(WebRequestFilter::class.java) }

    override fun intercept(request: WebGraphQlRequest, chain: WebGraphQlInterceptor.Chain): Mono<WebGraphQlResponse> {
        val userId = getHeaderAuthToken(request.headers).map { tok ->
            try {
                val jwtClaim = jwtParser.parseClaimsJws(tok)
                jwtClaim.body.subject.toLong() // UserId
            } catch (e: JwtException) {
                logger.error("JWT Parsing", e)
            }

            -1L
        }

        logger.info("JWT Token", userId.get())

        request.configureExecutionInput { _, rxBuilder ->
            rxBuilder.graphQLContext { ctxBuilder -> ctxBuilder.put(USERID_KEY, userId.get()) }.build()
        }

        return chain.next(request)
    }
}
