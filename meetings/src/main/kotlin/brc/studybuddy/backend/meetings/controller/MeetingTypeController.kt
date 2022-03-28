package brc.studybuddy.backend.meetings.controller

import brc.studybuddy.backend.meetings.repository.MeetingsRepository
import brc.studybuddy.model.Group
import brc.studybuddy.model.Meeting
import brc.studybuddy.webclient.extension.graphQlQuery
import brc.studybuddy.webclient.extension.graphQlToMono
import com.netflix.discovery.converters.Auto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Controller
@SchemaMapping(typeName = "Meeting")
class MeetingTypeController {

    @Autowired
    private lateinit var meetingsRepository: MeetingsRepository

    @Autowired
    private lateinit var webClientBuilder : WebClient.Builder

    private val usersWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://users/users").build()}

    private val groupsWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://groups/groups").build()}

    @SchemaMapping(field = "group")
    fun getFieldGroup(meeting: Meeting): Mono<Group> = groupsWebClient.post()
        .graphQlQuery("groupById(id:${meeting.groupId} ){id title description}")
        .retrieve()
        .graphQlToMono(Group::class.java)
}