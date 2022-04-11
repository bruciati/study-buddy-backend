package brc.studybuddy.backend.auth.controller

import brc.studybuddy.backend.auth.model.AuthError
import brc.studybuddy.backend.auth.model.AuthResponse
import brc.studybuddy.backend.auth.model.AuthSuccess
import brc.studybuddy.backend.auth.service.AuthService
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(value = ["auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthController {

    @Autowired
    lateinit var authService: AuthService

    @PostMapping
    fun authenticate(@RequestBody user: User): Mono<AuthResponse> =
        authService.authenticate(user)
            .onErrorMap { e -> AuthError(401, e.message ?: "") }
            .map { s -> AuthSuccess(s) }

}
