package brc.studybuddy.backend.auth.controllers

import reactor.core.publisher.Mono
import org.springframework.http.MediaType
import brc.studybuddy.backend.auth.models.ServiceResponse
import brc.studybuddy.backend.auth.repositories.UserRepository
import brc.studybuddy.backend.auth.util.toServiceResponse
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthController {

    @Autowired
    lateinit var userRepository: UserRepository

    // TODO: WIP
    @GetMapping("{user}")
    fun authenticate(@PathVariable("user") user: String): Mono<ServiceResponse<User>> =
        userRepository.findFirstByEmail(user)
            .switchIfEmpty(Mono.error(Exception("User not found")))
            .toServiceResponse()

    @PutMapping("{user}")
    fun register(@PathVariable user: User): Mono<ServiceResponse<String>> =
        userRepository.save(user)
            .map {"Success"}
            .toServiceResponse()

}