package brc.studybuddy.backend.users.service

import brc.studybuddy.input.MeetingAttendeeInput
import brc.studybuddy.input.MeetingInput
import brc.studybuddy.model.Meeting
import brc.studybuddy.model.MeetingAttendee
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class MeetingsClientService {
    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder
    private val webClient: WebClient by lazy(webClientBuilder.baseUrl("lb://meetings")::build)


    fun addMeeting(input: MeetingInput): Mono<Meeting> = webClient.post()
        .uri("/meetings")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(Meeting::class.java)

    fun addMeetingAttendee(input: MeetingAttendeeInput): Mono<MeetingAttendee> = webClient.post()
        .uri("/attendees")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(MeetingAttendee::class.java)


    fun updateMeeting(id: Long, input: MeetingInput): Mono<Meeting> = webClient.put()
        .uri("/meetings/$id")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(Meeting::class.java)

    fun updateMeetingAttendee(id: Long, input: MeetingAttendeeInput): Mono<MeetingAttendee> = webClient.put()
        .uri("/attendees/$id")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(MeetingAttendee::class.java)


    fun deleteMeeting(id: Long): Mono<Void> = webClient.delete()
        .uri("/meetings/$id")
        .retrieve()
        .bodyToMono(Void::class.java)

    fun deleteMeetingAttendee(id: Long): Mono<Void> = webClient.delete()
        .uri("/attendees/$id")
        .retrieve()
        .bodyToMono(Void::class.java)
}
