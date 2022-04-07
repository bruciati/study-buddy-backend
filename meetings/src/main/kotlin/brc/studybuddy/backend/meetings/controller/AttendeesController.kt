package brc.studybuddy.backend.meetings.controller

import brc.studybuddy.backend.meetings.repository.MeetingAttendeesRepository
import brc.studybuddy.input.MeetingAttendeeInput
import brc.studybuddy.model.MeetingAttendee
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.io.path.Path

@RestController
@RequestMapping(value = ["attendees"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AttendeesController {

    lateinit var meetingAttendeesRepository: MeetingAttendeesRepository

    @PutMapping
    fun save(@RequestBody input: MeetingAttendeeInput): Mono<MeetingAttendee> =
        Mono.just(input).map(MeetingAttendeeInput::toModel)

    @DeleteMapping("/meeting/{id}")
    fun deleteAllByMeetingId(@PathVariable id: Long) : Mono<Void> = meetingAttendeesRepository.deleteAllByMeetingId(id)

    @DeleteMapping("/user/{id}")
    fun deleteAllByUserId(@PathVariable id: Long): Mono<Void> = meetingAttendeesRepository.deleteAllByUserId(id)

    @DeleteMapping(path = ["/meeting/{meetingId}/user/{userId}", "/user/{userId}/meeting/{meetingId}"])
    fun deleteByUserIdAndMeetingId(@PathVariable userId: Long, @PathVariable meetingId: Long): Mono<Void> =
        meetingAttendeesRepository.deleteByUserIdAndAndMeetingId(userId, meetingId)

}