package brc.studybuddy.backend.gateway.auth

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.JwtParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
@Order(Int.MIN_VALUE)
class WebRequestFilter : WebFilter {
    @Autowired
    lateinit var jwtParser: JwtParser

    val logger: Logger by lazy { LoggerFactory.getLogger(WebRequestFilter::class.java) }

    private fun genAuthorizedExchange(headers: HttpHeaders): Boolean {
        val authToken = getHeaderAuthToken(headers)
        if (authToken.isPresent) {
            try {
                jwtParser.parseClaimsJws(authToken.get())
                return true
            } catch (e: JwtException) {
                logger.error("JWT Authentication", e)
            }
        }

        return false
    }

    // Webflux filter
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val reqPath = exchange.request.path.value()
        val isClientAuthorized = genAuthorizedExchange(exchange.request.headers)

        return when (isClientAuthorized || reqPath.startsWith("/auth")) {
            true -> chain.filter(exchange)
            false -> with(exchange.response) {
                statusCode = HttpStatus.UNAUTHORIZED
                headers.add(
                    "WWW-Authenticate",
                    "Bearer realm=\"Access to the user's private area\", charset=\"UTF-8\""
                )
                setComplete()
            }
        }
    }
}
