package brc.studybuddy.backend.auth.controller

import brc.studybuddy.backend.auth.model.AuthResponse
import brc.studybuddy.backend.auth.model.AuthSuccess
import brc.studybuddy.backend.auth.service.AuthService
import brc.studybuddy.backend.auth.service.RegisterService
import brc.studybuddy.input.UserInput
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping(value = ["register"], produces = [MediaType.APPLICATION_JSON_VALUE])
class RegisterController {

    @Autowired
    lateinit var registerService: RegisterService
    @Autowired
    lateinit var authService: AuthService

    @PostMapping
    fun register(@RequestBody email: String, @RequestBody password: String): Mono<AuthResponse> =
        registerService.register(UserInput(email, User.AuthType.PASSWORD, password))
            .flatMap { authService.authenticate(UserInput(it.email, it.authType, it.authValue)) }
            .map { s -> AuthSuccess(s.first, s.second) }

}