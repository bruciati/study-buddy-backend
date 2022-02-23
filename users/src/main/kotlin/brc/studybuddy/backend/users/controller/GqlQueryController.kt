package brc.studybuddy.backend.users.controller

import brc.studybuddy.backend.users.repository.UsersRepository
import brc.studybuddy.database.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class GqlQueryController {
    @Autowired
    private lateinit var usersRepository: UsersRepository

    @QueryMapping
    fun users(): Flux<User> = usersRepository.findAll()

    @QueryMapping
    fun userById(@Argument id: Long): Mono<User> = usersRepository.findById(id)

    @QueryMapping
    fun userByEmail(@Argument email: String): Mono<User> = usersRepository.findByEmail(email)
}
