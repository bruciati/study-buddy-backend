package brc.studybuddy.backend.groups.graphql

import brc.studybuddy.backend.groups.repository.GroupMembersRepository
import brc.studybuddy.backend.groups.repository.GroupsRepository
import brc.studybuddy.model.Group
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Controller
class MutationTypeController {
    @Autowired
    private lateinit var groupsRepository: GroupsRepository

    @Autowired
    private lateinit var groupMembersRepository: GroupMembersRepository

    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder

    private val usersWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://users/users").build() }

    @MutationMapping
    fun addGroup(
        @Argument name: String,
        @Argument description: String?
    ): Mono<Group> = TODO()

    @MutationMapping
    fun updateGroup(
        @Argument id: Long,
        @Argument name: String?,
        @Argument description: String?
    ): Mono<Group> = TODO()
}
