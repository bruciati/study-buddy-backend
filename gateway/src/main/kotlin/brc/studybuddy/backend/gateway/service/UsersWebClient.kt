package brc.studybuddy.backend.gateway.service

import brc.studybuddy.backend.gateway.service.extra.GroupMembersActions
import brc.studybuddy.backend.gateway.service.extra.MeetingAttendeesActions
import brc.studybuddy.input.UserInput
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class UsersWebClient : GroupMembersActions, MeetingAttendeesActions {
    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder
    override val webClient by lazy { webClientBuilder.baseUrl("lb://users").build() }


    fun saveUser(input: UserInput): Mono<User> = webClient.post()
        .uri("/users")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(User::class.java)


    fun getUsers(ids: Optional<List<Long>>): Flux<User> = webClient.get()
        .uri { b -> b.path("/users").queryParamIfPresent("id", ids).build() }
        .retrieve()
        .bodyToFlux(User::class.java)

    fun getUsersByGroupId(id: Long, isOwner: Optional<Boolean>): Flux<User> = webClient.get()
        .uri { b -> b.path("/users/group/$id").queryParamIfPresent("is_owner", isOwner).build() }
        .retrieve()
        .bodyToFlux(User::class.java)

    fun getUsersByMeetingId(id: Long, isHost: Optional<Boolean>): Flux<User> = webClient.get()
        .uri { b -> b.path("/users/meeting/$id").queryParamIfPresent("is_host", isHost).build() }
        .retrieve()
        .bodyToFlux(User::class.java)

    fun getUser(id: Long): Mono<User> = webClient.get()
        .uri("/users/$id")
        .retrieve()
        .bodyToMono(User::class.java)

    fun getUserByEmail(email: String): Mono<User> = webClient.get()
        .uri("/users/email/$email")
        .retrieve()
        .bodyToMono(User::class.java)
}
