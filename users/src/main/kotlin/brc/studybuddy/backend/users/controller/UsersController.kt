package brc.studybuddy.backend.users.controller

import brc.studybuddy.backend.users.repository.UserRepository
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class UsersController {
    @Autowired
    lateinit var userRepository: UserRepository

    @QueryMapping
    fun users(): Flux<User> = userRepository.findAll()

    @QueryMapping
    fun userById(id: UInt): Mono<User> = userRepository.findById(id)
}
