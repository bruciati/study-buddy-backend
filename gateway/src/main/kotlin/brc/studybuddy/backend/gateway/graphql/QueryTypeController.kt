package brc.studybuddy.backend.gateway.graphql

import brc.studybuddy.model.Group
import brc.studybuddy.model.Meeting
import brc.studybuddy.model.User
import brc.studybuddy.webclient.extension.graphQlQuery
import brc.studybuddy.webclient.extension.graphQlToFlux
import brc.studybuddy.webclient.extension.graphQlToMono
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
    private lateinit var webClientBuilder: WebClient.Builder

    private val usersWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://users/users").build() }
    private val groupsWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://groups/groups").build() }
    private val meetingsWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://meetings/meetings").build() }


    // ------------------------------
    // Users queries
    @QueryMapping
    fun users(@Argument ids: List<Long>?): Flux<User> = usersWebClient.post()
        .graphQlQuery("users(ids: $ids) { id email }")
        .retrieve()
        .graphQlToFlux(User::class.java)

    @QueryMapping
    fun userById(@Argument id: Long): Mono<User> = usersWebClient.post()
        .graphQlQuery("userById(id: $id) { id email }")
        .retrieve()
        .graphQlToMono(User::class.java)


    // ------------------------------
    // Groups queries
    @QueryMapping
    fun groups(@Argument ids: List<Long>?): Flux<Group> = groupsWebClient.post()
        .graphQlQuery("groups(ids: $ids) { id title description }")
        .retrieve()
        .graphQlToFlux(Group::class.java)

    @QueryMapping
    fun groupById(@Argument id: Long): Mono<Group> = groupsWebClient.post()
        .graphQlQuery("groupById(id: $id) { id title description }")
        .retrieve()
        .graphQlToMono(Group::class.java)


    // ------------------------------
    // Meetings queries
    @QueryMapping
    fun meetings(@Argument ids: List<Long>?): Flux<Meeting> = meetingsWebClient.post()
        .graphQlQuery("meetings(ids: $ids) { id name groupId dateTime type location }")
        .retrieve()
        .graphQlToFlux(Meeting::class.java)

    @QueryMapping
    fun meetingById(@Argument id: Long): Mono<Meeting> = meetingsWebClient.post()
        .graphQlQuery("meetingById(id: $id) { id name groupId dateTime type location }")
        .retrieve()
        .graphQlToMono(Meeting::class.java)
}
