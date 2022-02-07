package brc.studybuddy.backend.auth.controllers

import reactor.core.publisher.Mono
import org.springframework.http.MediaType
import brc.studybuddy.backend.auth.models.AuthToken
import brc.studybuddy.backend.auth.models.ServiceResponse
import brc.studybuddy.backend.auth.util.toServiceResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthController {

    @GetMapping
    fun handleGet(): Mono<ServiceResponse<AuthToken>> =
        Mono.just(AuthToken("j5n3wsxj09j3", 345897u))
            .toServiceResponse()
}