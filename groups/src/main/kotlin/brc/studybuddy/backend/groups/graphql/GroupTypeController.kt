package brc.studybuddy.backend.groups.graphql

import brc.studybuddy.backend.groups.model.GroupMember
import brc.studybuddy.backend.groups.repository.GroupMembersRepository
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
    private lateinit var groupMembersRepository: GroupMembersRepository

    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder

    private val usersWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://users/users").build() }

    private val meetingsWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://meetings/meetings").build() }

    //@SchemaMapping(field = "id")
    //fun getFieldId(group: Group): Mono<Long> = Mono.just(group.id)

    @SchemaMapping(field = "owner")
    fun getFieldOwner(group: Group): Mono<User> = groupMembersRepository.findByGroupIdAndIsOwner(group.id)
        .flatMap { m ->
            usersWebClient.post()
                .graphQlQuery("userById(id: ${m.userId}) { id email }")
                .retrieve()
                .graphQlToMono(User::class.java)
        }

    //@SchemaMapping(field = "title")
    //fun getFieldTitle(group: Group): Mono<String> = Mono.just(group.title)

    //@SchemaMapping(field = "description")
    //fun getFieldDescription(group: Group): Mono<String> = Mono.just(group.description)

    @SchemaMapping(field = "members")
    fun getFieldMembers(group: Group): Flux<User> = groupMembersRepository.findAllByGroupId(group.id)
        //.filter { m -> !m.isOwner }
        .map(GroupMember::groupId)
        .collectList()
        .flatMapMany { l ->
            usersWebClient.post()
                .graphQlQuery("users(ids: ${l}) { id email }")
                .retrieve()
                .graphQlToFlux(User::class.java)
        }

    @SchemaMapping(field = "meetings")
    fun getFieldMeetings(group: Group): Flux<Meeting> = meetingsWebClient.post()
        .graphQlQuery("meetingsByGroupId(id: ${group.id}) { id name datetime type location }")
        .retrieve()
        .graphQlToFlux(Meeting::class.java)
}
