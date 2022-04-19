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
import java.util.*
import java.util.function.Predicate.not
import java.util.regex.Matcher
import java.util.regex.Pattern

const val USERID_HEADER = "X-UserID"

private const val AUTHORIZATION_HEADER = "Authorization"
private val BEARER_PATTERN: Pattern = Pattern.compile("^Bearer (.+?)$")

@Component
@Order(Int.MIN_VALUE)
class WebRequestFilter : WebFilter {
    @Autowired
    lateinit var authConfig: AuthConfig


    // TODO Implement unauthenticated request filtering
    private fun isClientAuthorized(headers: HttpHeaders): Boolean {
        val authToken = getToken(headers)

        val tokenUserId = 1L
        headers.remove(USERID_HEADER)
        headers.add(USERID_HEADER, tokenUserId.toString())

        return true
    }

    private fun getToken(headers: HttpHeaders): Optional<String> = Optional
        .ofNullable(headers.getFirst(AUTHORIZATION_HEADER))
        .filter(not(String::isEmpty))
        .map(BEARER_PATTERN::matcher)
        .filter(Matcher::find)
        .map { m -> m.group(1) }

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
