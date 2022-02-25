package brc.studybuddy.backend.gateway

import io.jsonwebtoken.*
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.security.Key
import javax.annotation.PostConstruct

@Component
class AuthGatewayFilterFactory : AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config>(Config::class.java) {

    @Autowired
    lateinit var key: Key
    lateinit var tokenParser: JwtParser

    @PostConstruct
    fun finalizeConstruction() {
        tokenParser = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
    }

    class Config

    class AuthError(message: String?) : Exception(message)

    override fun apply(config: Config?): GatewayFilter =
        GatewayFilter { exchange, chain ->
            val token: String? = exchange
                .request
                .headers
                .getFirst(HttpHeaders.AUTHORIZATION)

            val checkToken = Mono.justOrEmpty(token)
                .switchIfEmpty(Mono.error(AuthError("No bearer token provided")))
                .map(tokenParser::parseClaimsJws)
                .onErrorMap { e ->
                    when(e) {
                        is ExpiredJwtException -> AuthError("Bearer token expired")
                        is MalformedJwtException -> AuthError("Bearer token malformed")
                        is SecurityException -> AuthError("Token not valid")
                        else -> e
                    }
                }
                .onErrorResume { e ->
                    Mono.fromRunnable {
                        val headerMessage = "Bearer realm=\"${e.localizedMessage}\""
                        exchange.response.headers.add("WWW-Authenticate", headerMessage)
                        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                    }
                }

            chain.filter(exchange)
                .then(checkToken)
                .then()
        }

}