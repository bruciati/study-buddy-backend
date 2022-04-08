package brc.studybuddy.backend.meetings.controller

import brc.studybuddy.backend.meetings.repository.MeetingAttendeesRepository
import brc.studybuddy.input.MeetingAttendeeInput
import brc.studybuddy.model.MeetingAttendee
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(value = ["attendees"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AttendeesController {
    @Autowired
    lateinit var meetingAttendeesRepository: MeetingAttendeesRepository

    @PutMapping
    fun save(@RequestBody input: MeetingAttendeeInput): Mono<MeetingAttendee> = Mono.just(input)
        .map(MeetingAttendeeInput::toModel)
        .flatMap(meetingAttendeesRepository::save)

    @DeleteMapping("/meeting/{id}")
    fun deleteAllByMeetingId(@PathVariable id: Long): Mono<Boolean> =
        meetingAttendeesRepository.deleteAllByMeetingId(id)
            .thenReturn(true)
            .onErrorReturn(false)

    @DeleteMapping("/user/{id}")
    fun deleteAllByUserId(@PathVariable id: Long): Mono<Boolean> =
        meetingAttendeesRepository.deleteAllByUserId(id)
            .thenReturn(true)
            .onErrorReturn(false)

    @DeleteMapping(path = ["/meeting/{meetingId}/user/{userId}", "/user/{userId}/meeting/{meetingId}"])
    fun deleteByUserIdAndMeetingId(@PathVariable meetingId: Long, @PathVariable userId: Long): Mono<Boolean> =
        meetingAttendeesRepository.deleteByUserIdAndMeetingId(userId, meetingId)
            .thenReturn(true)
            .onErrorReturn(false)
}