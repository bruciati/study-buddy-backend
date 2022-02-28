package brc.studybuddy.backend.users.controller

import brc.studybuddy.backend.users.repository.UsersRepository
import brc.studybuddy.model.Group
import brc.studybuddy.model.User
import brc.studybuddy.webclient.extension.bodyGraphQl
import brc.studybuddy.webclient.extension.graphQlToFlux
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class GqlQueryController {
    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder

    private val groupsWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://groups/groups").build() }

    @QueryMapping
    fun users(): Flux<User> = usersRepository.findAll()

    @QueryMapping
    fun userById(@Argument id: Long): Mono<User> = usersRepository.findById(id)
        .flatMap { user ->
            groupsWebClient.post()
                .bodyGraphQl("groupsByOwnerId(id: $id) { id title description }")
                .retrieve()
                .graphQlToFlux(Group::class.java)
                .doOnNext { g -> g.owner = user }
                .collectList()
                .doOnNext { groups -> user.groups = groups }
                .map { user }
                .onErrorReturn(user)
        }

    @QueryMapping
    fun userByEmail(@Argument email: String): Mono<User> = usersRepository.findByEmail(email)
}
