package brc.studybuddy.backend.template.controller

import brc.studybuddy.backend.wrapper.model.Response
import brc.studybuddy.model.Group
import brc.studybuddy.model.LoginType
import brc.studybuddy.model.User
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(value = ["template"], produces = [MediaType.APPLICATION_JSON_VALUE])
class TemplateController {
    // Handles GET request of "localhost:8080/template"
    @GetMapping
    fun defaultHandler(): Mono<Group> = Mono.just(Group(1, 2, "Group", "This is a group"))

    // Handles GET request of "localhost:8080/template/flux"
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

    // Handles GET request of "localhost:8080/template/error"
    @GetMapping("error")
    fun errorHandler(): Mono<String> =
        Mono.error(Exception("This error will be logged but not returned (Code: 500 - INTERNAL SERVER ERROR)"))

    // Handles GET request of "localhost:8080/template/error/custom"
    @GetMapping("error/custom")
    fun customErrorHandler(): Mono<String> =
        Mono.error(Response.Error(HttpStatus.BAD_REQUEST, "This error will be logged and returned (Custom code)"))
}
