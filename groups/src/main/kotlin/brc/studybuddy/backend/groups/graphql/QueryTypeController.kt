package brc.studybuddy.backend.groups.graphql

import brc.studybuddy.backend.groups.model.GroupMember
import brc.studybuddy.backend.groups.repository.GroupMembersRepository
import brc.studybuddy.backend.groups.repository.GroupsRepository
import brc.studybuddy.model.Group
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class QueryTypeController {
    @Autowired
    private lateinit var groupsRepository: GroupsRepository

    @Autowired
    private lateinit var groupMembersRepository: GroupMembersRepository

    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder

    private val usersWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://users/users").build() }

    @QueryMapping
    fun groups(@Argument ids: List<Long>?): Flux<Group> = Mono.justOrEmpty(ids)
        .flatMapMany(groupsRepository::findAllById)
        .switchIfEmpty(groupsRepository.findAll())

    @QueryMapping
    fun groupsByOwnerId(@Argument id: Long): Flux<Group> = groupMembersRepository.findAllByUserIdAndIsOwner(id)
        .map(GroupMember::groupId)
        .collectList()
        .flatMapMany(groupsRepository::findAllById)

    @QueryMapping
    fun groupsByUserId(@Argument id: Long): Flux<Group> = groupMembersRepository.findAllByUserId(id)
        .map(GroupMember::groupId)
        .collectList()
        .flatMapMany(groupsRepository::findAllById)

    @QueryMapping
    fun groupById(@Argument id: Long): Mono<Group> = groupsRepository.findById(id)

    @QueryMapping
    fun groupByTitle(@Argument title: String): Mono<Group> = groupsRepository.findByTitle(title)
}
