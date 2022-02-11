package brc.studybuddy.backend.template.controller

import brc.studybuddy.model.LoginType
import brc.studybuddy.model.User
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@CrossOrigin("*")
@RequestMapping(value = ["template"], produces = [MediaType.APPLICATION_JSON_VALUE])
class TemplateController {
    @GetMapping
    fun defaultHandler(): Mono<User> = Mono.just(User(1u, "test@email.com", LoginType.PASSWORD, "sha256::pwd123", 0u))

    @GetMapping("flux")
    fun fluxHandler(): Flux<User> = Flux.fromArray(
        arrayOf(
            User(1u, "test1@email.com", LoginType.PASSWORD, "sha256::pwd123", 0u),
            User(2u, "test2@email.com", LoginType.FACEBOOK, "sha256::pwd123", 4u),
            User(3u, "test3@email.com", LoginType.PASSWORD, "sha256::pwd123", 7u),
            User(4u, "test4@email.com", LoginType.FACEBOOK, "sha256::pwd123", 9u),
            User(5u, "test5@email.com", LoginType.FACEBOOK, "sha256::pwd123", 0u),
            User(6u, "test6@email.com", LoginType.PASSWORD, "sha256::pwd123", 1u)
        )
    )

    @GetMapping("error")
    fun errorHandler(): Mono<User> = Mono.error(Exception("It's a good error! :D"))
}
