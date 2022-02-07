package brc.studybuddy.backend.auth.controllers

import reactor.core.publisher.Mono
import org.springframework.http.MediaType
import brc.studybuddy.backend.auth.models.ServiceResponse
import brc.studybuddy.backend.auth.models.User
import brc.studybuddy.backend.auth.repositories.UserRepository
import brc.studybuddy.backend.auth.util.toServiceResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthController {

    @Autowired
    lateinit var userRepository: UserRepository

    // TODO: WIP
    @GetMapping
    fun handleGet(): Mono<ServiceResponse<List<User>>> =
        userRepository.findAll()
            .toServiceResponse()

    // TODO: Doesn't handle exceptions properly, we need to find out a way to catch exceptions (WebFilter)
    @GetMapping("{id}")
    fun handleGet(@PathVariable id: Int) =
        userRepository.findById(id)
            .toServiceResponse()
}