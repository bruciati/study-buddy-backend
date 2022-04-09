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

private const val ENDPOINT = "/meetings"

@Service
class MeetingsWebClient : MeetingAttendeesActions {
    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder
    override val webClient by lazy { webClientBuilder.baseUrl("lb://meetings").build() }


    fun saveMeeting(input: MeetingInput): Mono<Meeting> = webClient.post()
        .uri(ENDPOINT)
        .bodyValue(input)
        .retrieve()
        .bodyToMono(Meeting::class.java)

    fun updateMeeting(id: Long, input: MeetingInput): Mono<Meeting> = webClient.put()
        .uri("$ENDPOINT/$id")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(Meeting::class.java)

    fun deleteMeeting(id: Long): Mono<Boolean> = webClient.delete()
        .uri("$ENDPOINT/$id")
        .retrieve()
        .bodyToMono(Boolean::class.java)


    fun getMeetingsByGroupId(id: Long): Flux<Meeting> = webClient.get()
        .uri("$ENDPOINT/group/$id")
        .retrieve()
        .bodyToFlux(Meeting::class.java)

    fun getMeetingsByUserId(id: Long, isHost: Optional<Boolean>): Flux<Meeting> = webClient.get()
        .uri { b -> b.path("$ENDPOINT/user/$id").queryParamIfPresent("is_host", isHost).build() }
        .retrieve()
        .bodyToFlux(Meeting::class.java)

    fun getMeeting(id: Long): Mono<Meeting> = webClient.get()
        .uri("$ENDPOINT/$id")
        .retrieve()
        .bodyToMono(Meeting::class.java)
}
