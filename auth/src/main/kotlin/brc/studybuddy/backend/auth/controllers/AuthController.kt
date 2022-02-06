package brc.studybuddy.backend.auth.controllers

import brc.studybuddy.backend.auth.models.LoginResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/auth")
class AuthController {

    @GetMapping
    fun handleGet(): Mono<LoginResponse> =
         Mono.just(LoginResponse(true, "j5n3wsxj09j3"))

}