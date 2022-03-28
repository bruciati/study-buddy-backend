package brc.studybuddy.backend.meetings.controller

import brc.studybuddy.model.Group
import brc.studybuddy.model.User
import brc.studybuddy.webclient.extension.graphQlQuery
import brc.studybuddy.webclient.extension.graphQlToFlux
import brc.studybuddy.webclient.extension.graphQlToMono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
@SchemaMapping(typeName = "Group")
class GroupTypeController {
    @Autowired
    private lateinit var webClientBuilder : WebClient.Builder

    private val groupsWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://groups/groups").build()}

    @SchemaMapping(field = "owner")
    fun getFieldOwner(group: Group) : Mono<User> = groupsWebClient.post()
        .graphQlQuery("ownerByGroupId(id: ${group.id}){id email}")
        .retrieve()
        .graphQlToMono(User::class.java)

    @SchemaMapping(field = "members")
    fun getFieldMembers(group: Group) : Flux<User> = groupsWebClient.post()
        .graphQlQuery("membersByGroupId(id: ${group.id}){id email}")
        .retrieve()
        .graphQlToFlux(User::class.java)
}