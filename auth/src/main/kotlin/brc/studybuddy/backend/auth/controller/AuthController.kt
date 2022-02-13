package brc.studybuddy.backend.auth.controller

import reactor.core.publisher.Mono
import org.springframework.http.MediaType
import brc.studybuddy.backend.auth.repository.UserRepository
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthController {

    @Autowired
    lateinit var userRepository: UserRepository

    // TODO: WIP
    @GetMapping("{user}")
    fun authenticate(@PathVariable("user") user: String): Mono<User> =
        userRepository.findFirstByEmail(user)
            .switchIfEmpty(Mono.error(Exception("User not found")))


    @PutMapping("{user}")
    fun register(@PathVariable user: User): Mono<String> =
        userRepository.save(user)
            .map {"Success"}

}