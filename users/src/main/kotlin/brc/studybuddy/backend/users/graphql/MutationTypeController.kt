package brc.studybuddy.backend.users.graphql

import brc.studybuddy.backend.users.model.UserInput
import brc.studybuddy.backend.users.repository.UsersRepository
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

@Controller
class MutationTypeController {
    @Autowired
    private lateinit var usersRepository: UsersRepository

    @MutationMapping
    fun addUser(
        @Argument input: UserInput
    ): Mono<User> = Mono.just(input.toUser())
        .flatMap(usersRepository::save)

    @MutationMapping
    fun updateUser(
        @Argument id: Long,
        @Argument input: UserInput
    ): Mono<User> = usersRepository.findById(id)
        .map(input::updateUser)
        .flatMap(usersRepository::save)

    // TODO delete data from other services
    @MutationMapping
    fun deleteUser(
        @Argument id: Long
    ): Mono<Boolean> = usersRepository.deleteById(id)
        .thenReturn(true) // Can not be 'false'
}
