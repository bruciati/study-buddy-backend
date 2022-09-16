package brc.studybuddy.backend.gateway.auth

import graphql.schema.DataFetcher
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.JwtParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.*
import java.util.function.Predicate
import java.util.regex.Matcher
import java.util.regex.Pattern

const val USERID_KEY = "X-UserId"

private const val AUTHORIZATION_HEADER = "Authorization"
private val BEARER_PATTERN: Pattern = Pattern.compile("^Bearer (.+?)$")


internal fun getHeaderAuthToken(headers: HttpHeaders): Optional<String> =
    Optional.ofNullable(headers.getFirst(AUTHORIZATION_HEADER))
        .filter(Predicate.not(String::isEmpty))
        .map(BEARER_PATTERN::matcher)
        .filter(Matcher::find)
        .map { m -> m.group(1) }


@Component
@Order(Int.MIN_VALUE)
class WebRequestFilter : WebFilter {
    @Autowired
    lateinit var jwtParser: JwtParser

    val logger: Logger by lazy { LoggerFactory.getLogger(WebRequestFilter::class.java) }

    private fun genAuthorizedExchange(request: ServerHttpRequest): Boolean {
        val authToken = getHeaderAuthToken(request.headers)
        if (authToken.isPresent) {
            try {
                val jwt = jwtParser.parseClaimsJws(authToken.get())

                DataFetcher {
                    it.graphQlContext.put(
                        USERID_KEY,
                        jwt.body.subject.toLong()
                    )
                }

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
        val isClientAuthorized = genAuthorizedExchange(exchange.request)

        // Since CORS send a pre-flight OPTIONS request, we need to let every OPTION request in
        return when (
            isClientAuthorized ||
                    reqPath.startsWith("/auth") ||
                    exchange.request.method == HttpMethod.OPTIONS
        ) {
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
