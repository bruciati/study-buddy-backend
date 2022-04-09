package brc.studybuddy.backend.gateway.auth

import brc.studybuddy.backend.gateway.config.AuthConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class WebRequestFilter : WebFilter {
    @Autowired
    lateinit var authConfig: AuthConfig

    private fun isClientAuthorized(exchange: ServerWebExchange, chain: WebFilterChain): Boolean {
        // TODO Implement unauthenticated request filtering
        return true
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val pathString = exchange.request.path.value()

        if (!pathString.startsWith("/auth"))
            if (!isClientAuthorized(exchange, chain))
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED

        return chain.filter(exchange)
    }
}
