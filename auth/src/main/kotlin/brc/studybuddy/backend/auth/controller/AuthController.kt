package brc.studybuddy.backend.auth.controller

import brc.studybuddy.backend.auth.model.AuthError
import brc.studybuddy.backend.auth.model.AuthResponse
import brc.studybuddy.backend.auth.model.AuthSuccess
import brc.studybuddy.backend.auth.service.AuthService
import brc.studybuddy.input.UserInput
import brc.studybuddy.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(value = ["auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthController {

    @Autowired
    lateinit var authService: AuthService
    var logger: Logger? = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping
    fun authenticate(@RequestBody user: UserInput): Mono<AuthResponse> =
        authService.authenticate(user)
            .doFirst({ logger?.info("Got user ${user}") })
            .onErrorMap { e -> AuthError(401, e.message ?: "An unexpected error occurred") }
            .map { s -> AuthSuccess(s) }

}
