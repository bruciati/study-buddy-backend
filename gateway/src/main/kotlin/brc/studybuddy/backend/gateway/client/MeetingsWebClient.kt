package brc.studybuddy.backend.gateway.client

import brc.studybuddy.backend.gateway.client.extra.MeetingAttendeesActions
import brc.studybuddy.input.MeetingInput
import brc.studybuddy.model.Meeting
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class MeetingsWebClient : MeetingAttendeesActions {
    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder
    override val webClient by lazy { webClientBuilder.baseUrl("lb://meetings").build() }


    fun saveMeeting(input: MeetingInput): Mono<Meeting> = webClient.post()
        .uri("/meetings")
        .bodyValue(input::toModel)
        .retrieve()
        .bodyToMono(Meeting::class.java)


    fun getMeetingsByGroupId(id: Long): Flux<Meeting> = webClient.get()
        .uri("/meetings/group/$id")
        .retrieve()
        .bodyToFlux(Meeting::class.java)

    fun getMeetingsByUserId(id: Long, isHost: Optional<Boolean>): Flux<Meeting> = webClient.get()
        .uri { b -> b.path("/meetings/user/$id").queryParamIfPresent("is_host", isHost).build() }
        .retrieve()
        .bodyToFlux(Meeting::class.java)

    fun getMeeting(id: Long): Mono<Meeting> = webClient.get()
        .uri("/meetings/$id")
        .retrieve()
        .bodyToMono(Meeting::class.java)
}
