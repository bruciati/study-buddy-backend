package brc.studybuddy.backend.gateway.graphql

import brc.studybuddy.model.Group
import brc.studybuddy.model.Meeting
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
    private lateinit var webClientBuilder: WebClient.Builder

    private val usersWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://users/users").build() }
    private val meetingsWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://meetings/meetings").build() }

    @SchemaMapping(field = "owner")
    fun getFieldOwner(group: Group): Mono<User> = usersWebClient.post()
        .graphQlQuery("ownerByGroupId(id: ${group.id}) { id email }")
        .retrieve()
        .graphQlToMono(User::class.java)

    @SchemaMapping(field = "members")
    fun getFieldMembers(group: Group): Flux<User> = usersWebClient.post()
        .graphQlQuery("usersByGroupId(id: ${group.id}) { id email }")
        .retrieve()
        .graphQlToFlux(User::class.java)

    @SchemaMapping(field = "meetings")
    fun getFieldMeetings(group: Group): Flux<Meeting> = meetingsWebClient.post()
        .graphQlQuery("meetingsByGroupId(id: ${group.id}){ id name groupId dateTime type location }")
        .retrieve()
        .graphQlToFlux(Meeting::class.java)
}
