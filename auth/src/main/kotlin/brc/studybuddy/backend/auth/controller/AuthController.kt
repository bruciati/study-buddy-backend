package brc.studybuddy.backend.auth.controller

import brc.studybuddy.backend.auth.repository.UserRepository
import brc.studybuddy.database.model.User
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
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

    // Since JWTS depends on the private Key (which is Autowired) we're not exactly sure when the container
    // system will eventually autowire that parameter. We use @PostConstruct to be sure
    @PostConstruct
    fun finalizeConstruction() {
        signer = Jwts.builder().signWith(key)
    }

    // TODO: Should be put on a separate module
    fun generateToken(user: User): Token =
        signer.setSubject(user.id.toString())
            .setIssuedAt(Date.from(Instant.now()))
            .compact()

    @PostMapping
    fun authenticate(@RequestBody user: User): Mono<Token> =
        // TODO Rework authentication without lib
        Mono.just(user)
        //    .filter { u -> u.loginType == User.LoginType.PASSWORD }
        //    .switchIfEmpty(Mono.error(Response.Error(401, "Login type not allowed")))
        //    .flatMap { u -> userRepository.findFirstByEmailAndLoginValue(u.email, u.loginValue) }
        //    .switchIfEmpty(Mono.error(Response.Error(401, "Incorrect credentials")))
            .map(this::generateToken)

}