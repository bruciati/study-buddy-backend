package brc.studybuddy.backend.gateway.client.extra

import brc.studybuddy.backend.gateway.client.ATTENDEES_ENDPOINT
import brc.studybuddy.input.MeetingAttendeeInput
import brc.studybuddy.model.MeetingAttendee
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

internal interface MeetingAttendeesActions {
    val webClient: WebClient


    fun saveMeetingAttendee(input: MeetingAttendeeInput): Mono<MeetingAttendee> = webClient.post()
        .uri(ATTENDEES_ENDPOINT)
        .bodyValue(input)
        .retrieve()
        .bodyToMono(MeetingAttendee::class.java)


    fun deleteMeetingAttendeesByUserId(id: Long): Mono<Boolean> = webClient.delete()
        .uri("$ATTENDEES_ENDPOINT/user/$id")
        .retrieve()
        .bodyToMono(Boolean::class.java)

    fun deleteMeetingAttendeesByMeetingId(id: Long): Mono<Boolean> = webClient.delete()
        .uri("$ATTENDEES_ENDPOINT/meeting/$id")
        .retrieve()
        .bodyToMono(Boolean::class.java)

    fun deleteMeetingAttendeeByMeetingIdAndUserId(meetingId: Long, userId: Long): Mono<Boolean> = webClient.delete()
        .uri("$ATTENDEES_ENDPOINT/meeting/$meetingId/user/$userId")
        .retrieve()
        .bodyToMono(Boolean::class.java)
}
