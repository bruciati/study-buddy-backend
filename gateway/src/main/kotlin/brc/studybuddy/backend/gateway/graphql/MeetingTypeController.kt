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
@SchemaMapping(typeName = "Meeting")
class MeetingTypeController {
    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder

    private val usersWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://users/users").build() }
    private val groupsWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://groups/groups").build() }

    @SchemaMapping(field = "host")
    fun getFieldHost(meeting: Meeting): Mono<User> = usersWebClient.post()
        .graphQlQuery("hostByMeetingId(id: ${meeting.id}) { id email }")
        .retrieve()
        .graphQlToMono(User::class.java)

    @SchemaMapping(field = "group")
    fun getFieldGroup(meeting: Meeting): Mono<Group> = groupsWebClient.post()
        .graphQlQuery("groupById(id: ${meeting.groupId}) { id title description }")
        .retrieve()
        .graphQlToMono(Group::class.java)

    @SchemaMapping(field = "attendees")
    fun getFieldAttendees(meeting: Meeting): Flux<User> = usersWebClient.post()
        .graphQlQuery("usersByMeetingId(id: ${meeting.groupId}) { id email }")
        .retrieve()
        .graphQlToFlux(User::class.java)
}
