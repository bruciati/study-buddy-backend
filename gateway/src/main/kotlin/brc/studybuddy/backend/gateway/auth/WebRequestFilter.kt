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
    lateinit var jwtParser: JwtParser

    val logger: Logger by lazy { LoggerFactory.getLogger(WebRequestFilter::class.java) }

    private fun genAuthorizedExchange(exchange: ServerWebExchange): Optional<ServerWebExchange> =
        readHeaderToken(exchange.request.headers).map { tok ->
                try {
                    val jwtClaim = jwtParser.parseClaimsJws(tok)
                    val userId = jwtClaim.body.subject

                    val newRequest = exchange.request.mutate().header(USERID_HEADER, userId).build()
                    exchange.mutate().request(newRequest).build()
                } catch (e: JwtException) {
                    logger.error("Authentication", e)
                    null
                }
            }

    private fun readHeaderToken(headers: HttpHeaders): Optional<String> =
        Optional.ofNullable(headers.getFirst(AUTHORIZATION_HEADER))
            .filter(not(String::isEmpty))
            .map(BEARER_PATTERN::matcher)
            .filter(Matcher::find)
            .map { m -> m.group(1) }

    // Webflux filter
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val reqPath = exchange.request.path.value()
        if (reqPath.startsWith("/auth")) {
            return chain.filter(exchange)
        }

        // If "authExchange.isEmpty()" returns true, then the client isn't authorized
        val authExchange = genAuthorizedExchange(exchange)
        if (authExchange.isEmpty) {
            return with(exchange.response) {
                statusCode = HttpStatus.UNAUTHORIZED
                headers.add(
                    "WWW-Authenticate",
                    "Bearer realm=\"Access to the user's private area\", charset=\"UTF-8\""
                )
                setComplete()
            }
        }

        return chain.filter(authExchange.get())
    }
}
