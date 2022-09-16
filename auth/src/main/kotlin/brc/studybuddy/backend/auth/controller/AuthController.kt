package brc.studybuddy.backend.auth.controller

import brc.studybuddy.backend.auth.model.AuthResponse
import brc.studybuddy.backend.auth.model.AuthSuccess
import brc.studybuddy.backend.auth.service.AuthService
import brc.studybuddy.backend.auth.service.RegisterService
import brc.studybuddy.input.UserInput
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping(value = ["auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthController {
    @Autowired
    lateinit var authService: AuthService
    @Autowired
    lateinit var registerService: RegisterService

    val logger: Logger = LoggerFactory.getLogger("AuthenticationController")

    @PostMapping
    fun authenticate(@RequestBody user: UserInput): Mono<AuthResponse> =
        authService.authenticate(user)
            .map { s -> AuthSuccess(s.first, s.second) }

    @PostMapping(path = ["/refresh"])
    fun refresh(@RequestBody refreshToken: String): Mono<AuthResponse> =
        authService.refresh(refreshToken.removeSurrounding("\""))
            .map { s -> AuthSuccess(s.first, s.second) }

    @PutMapping
    fun register(@RequestBody user: UserInput): Mono<AuthResponse> =
        registerService.register(user)
            .flatMap { authService.authenticate(UserInput(it.email, user.firstName, user.lastName, it.authType, it.authValue)) }
            .map { s -> AuthSuccess(s.first, s.second) }

}
