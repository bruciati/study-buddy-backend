package brc.studybuddy.backend.auth.controller

import brc.studybuddy.backend.auth.model.AuthError
import brc.studybuddy.backend.auth.model.AuthResponse
import brc.studybuddy.backend.auth.model.AuthSuccess
import brc.studybuddy.backend.auth.repository.UserRepository
import brc.studybuddy.model.User
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.security.Key
import java.time.Instant
import java.util.*
import javax.annotation.PostConstruct

typealias Token = String

@RestController
@RequestMapping(value = ["auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthController {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var key: Key
    lateinit var signer: JwtBuilder

    @Value("\${secrets.ttl}")
    var ttl: Long = 0

    /*
     * Since JWT depends on the private Key (which is Autowired) we're not exactly sure when the container
     * system will eventually autowire that parameter. We use @PostConstruct to be sure that such method
     * will be executed after the bean creation.
     */
    @PostConstruct
    fun finalizeConstruction() {
        signer = Jwts.builder().signWith(key)
    }

    /*
     * Generate a new access token for the given user, with the given time to live (expressed in seconds)
     */
    fun generateToken(user: User): Token {
        val now = Instant.now()
        val expire = now.plusSeconds(ttl)
        return signer.setSubject(user.id.toString())
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(expire))
            .compact()
    }

    @PostMapping
    fun authenticate(@RequestBody user: User): Mono<AuthResponse> =
        Mono.just(user)
            .filter { u -> u.authType == User.AuthType.PASSWORD }
            .switchIfEmpty(Mono.error(AuthError(401, "Login type not allowed")))
            .flatMap { u -> userRepository.findFirstByEmailAndLoginValue(u.email, u.authValue) }
            .switchIfEmpty(Mono.error(AuthError(401, "Incorrect credentials")))
            .map { AuthSuccess(generateToken(user)) }

}
