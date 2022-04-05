package brc.studybuddy.backend.users.service

import brc.studybuddy.input.MeetingAttendeeInput
import brc.studybuddy.input.MeetingInput
import brc.studybuddy.model.Group
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
    private val webClient: WebClient by lazy(webClientBuilder.baseUrl("lb://meetings/meetings")::build)


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


    fun updateMeeting(meetingId: Long, input: MeetingInput): Mono<Meeting> = webClient.put()
        .uri("/meetings/$meetingId")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(Meeting::class.java)

    fun updateMeetingAttendee(userId: Long, input: MeetingAttendeeInput): Mono<MeetingAttendee> = webClient.put()
        .uri("/attendees/$userId")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(MeetingAttendee::class.java)


    fun deleteMeeting(meetingId: Long): Mono<Void> = webClient.delete()
        .uri("/meetings/$meetingId")
        .retrieve()
        .bodyToMono(Void::class.java)

    fun deleteMeetingAttendee(userId: Long): Mono<Void> = webClient.delete()
        .uri("/attendees/$userId")
        .retrieve()
        .bodyToMono(Void::class.java)
}
