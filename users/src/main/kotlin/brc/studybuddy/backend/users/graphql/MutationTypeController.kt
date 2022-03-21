package brc.studybuddy.backend.users.graphql

import brc.studybuddy.backend.users.repository.UsersRepository
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Controller
class MutationTypeController {
    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder

    private val groupsWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://groups/groups").build() }

    @MutationMapping
    fun addUser(
        @Argument email: String,
        @Argument loginType: User.LoginType? = User.LoginType.PASSWORD,
        @Argument loginValue: String?
        // TODO Could use "@Argument user: User" ??
    ): Mono<User> = TODO()

    @MutationMapping
    fun updateUser(
        @Argument id: Long,
        @Argument email: String?,
        @Argument loginType: User.LoginType?,
        @Argument loginValue: String?
        // TODO Could use "@Argument user: User" ??
    ): Mono<User> = TODO()
}
