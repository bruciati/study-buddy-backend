package brc.studybuddy.backend.auth.controller

import reactor.core.publisher.Mono
import org.springframework.http.MediaType
import brc.studybuddy.backend.auth.repository.UserRepository
import brc.studybuddy.backend.wrapper.model.Response
import brc.studybuddy.model.User
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.security.Key
import java.time.Instant
import java.util.*
import javax.crypto.spec.SecretKeySpec

@RestController
@RequestMapping(value = ["auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthController {

    @Autowired
    lateinit var userRepository: UserRepository

    // TODO: WIP - Should be reorganized
    private final val secret: String = "n5in2oadvf80qfjnk290pp58yk4jnasldkjhgf"
    private final val key: Key = SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.jcaName)

    fun generateToken(user: User): String =
        Jwts.builder()
            .setSubject(user.id.toString())
            .setIssuedAt(Date.from(Instant.now()))
            .signWith(key)
            .compact()

    // TODO: WIP
    @GetMapping("{user}")
    fun authenticate(@PathVariable("user") user: String): Mono<String> =
        userRepository
            .findFirstByEmail(user)
            .map(this::generateToken)
            .switchIfEmpty(Mono.error(Response.Error(300, "User not found")))

    @PutMapping("{user}")
    fun register(@PathVariable user: User): Mono<String> =
        userRepository
            .save(user)
            .map {"Success"}

}