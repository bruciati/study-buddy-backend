package brc.studybuddy.backend.meetings.controller

import brc.studybuddy.model.Group
import brc.studybuddy.model.User
import brc.studybuddy.webclient.extension.graphQlBody
import brc.studybuddy.webclient.extension.graphQlQuery
import brc.studybuddy.webclient.extension.graphQlToFlux
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Controller
@SchemaMapping(typeName = "User")
class UserTypeController {

    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder

    private val groupsWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://groups/groups").build()}

    @SchemaMapping(field = "groups")
    fun getFieldUser(user: User): Flux<Group> = groupsWebClient.post()
        .graphQlQuery("groupsByUserId(id: ${user.id}) {id title description}")
        .retrieve()
        .graphQlToFlux(Group::class.java)
}