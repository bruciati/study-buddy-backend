package brc.studybuddy.backend.gateway.auth

import brc.studybuddy.backend.gateway.config.AuthConfig
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
    lateinit var authConfig: AuthConfig


    // TODO Implement unauthenticated request filtering
    private fun isClientAuthorized(headers: HttpHeaders): Boolean {
        val authCode = headers.getFirst("Authorization")

        val tokenUserId = 1L
        headers.remove("X-UserID")
        headers.add("X-UserID", tokenUserId.toString())

        return true
    }


    // Webflux filter
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val reqPath = exchange.request.path.value()
        return when (isClientAuthorized(exchange.request.headers) || reqPath.startsWith("/auth")) {
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
