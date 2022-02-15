package brc.studybuddy.backend.template.controller

import brc.studybuddy.model.LoginType
import brc.studybuddy.model.User
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(value = ["template"], produces = [MediaType.APPLICATION_JSON_VALUE])
class TemplateController {
    @GetMapping
    fun defaultHandler(): Mono<User> = Mono.just(User(1, "test@email.com", LoginType.PASSWORD, "sha256::pwd123", 0))

    @GetMapping("flux")
    fun fluxHandler(): Flux<User> = Flux.fromArray(
        arrayOf(
            User(1, "test1@email.com", LoginType.PASSWORD, "sha256::pwd123", 0),
            User(2, "test2@email.com", LoginType.FACEBOOK, "sha256::pwd123", 4),
            User(3, "test3@email.com", LoginType.PASSWORD, "sha256::pwd123", 7),
            User(4, "test4@email.com", LoginType.FACEBOOK, "sha256::pwd123", 9),
            User(5, "test5@email.com", LoginType.FACEBOOK, "sha256::pwd123", 0),
            User(6, "test6@email.com", LoginType.PASSWORD, "sha256::pwd123", 1)
        )
    )

    @GetMapping("error")
    fun errorHandler(): Mono<User> = Mono.error(Exception("It's a good error! :D"))
}
