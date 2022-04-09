package brc.studybuddy.backend.gateway.auth

import brc.studybuddy.backend.gateway.config.AuthConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
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


    private fun isClientAuthorized(exchange: ServerWebExchange): Boolean {
        val authCode = exchange.request.headers.getFirst("Authorization")

        // TODO Implement unauthenticated request filtering
        return true
    }


    // Webflux filter
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val reqPath = exchange.request.path.value()
        return when (reqPath.startsWith("/auth") || isClientAuthorized(exchange)) {
            true -> chain.filter(exchange)
            false -> {
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                exchange.response.setComplete()
            }
        }
    }
}
