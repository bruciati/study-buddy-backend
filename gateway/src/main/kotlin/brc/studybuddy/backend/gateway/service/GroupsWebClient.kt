package brc.studybuddy.backend.gateway.service

import brc.studybuddy.backend.gateway.service.extra.GroupMembersActions
import brc.studybuddy.input.GroupInput
import brc.studybuddy.model.Group
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class GroupsWebClient : GroupMembersActions {
    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder
    override val webClient by lazy { webClientBuilder.baseUrl("lb://groups").build() }


    fun saveGroup(input: GroupInput): Mono<Group> = webClient.post()
        .uri("/groups")
        .bodyValue(input::toModel)
        .retrieve()
        .bodyToMono(Group::class.java)


    fun getGroups(ids: Optional<List<Long>>): Flux<Group> = webClient.get()
        .uri { b -> b.path("/groups").queryParamIfPresent("id", ids).build() }
        .retrieve()
        .bodyToFlux(Group::class.java)

    fun getGroupsByUserId(id: Long, isOwner: Optional<Boolean>): Flux<Group> = webClient.get()
        .uri { b -> b.path("/groups/user/$id").queryParamIfPresent("is_owner", isOwner).build() }
        .retrieve()
        .bodyToFlux(Group::class.java)

    fun getGroup(id: Long): Mono<Group> = webClient.get()
        .uri("/groups/$id")
        .retrieve()
        .bodyToMono(Group::class.java)

    fun getGroupByTitle(title: String): Mono<Group> = webClient.get()
        .uri("/groups/title/$title")
        .retrieve()
        .bodyToMono(Group::class.java)
}
