package brc.studybuddy.backend.meetings.controller

import brc.studybuddy.backend.meetings.repository.MeetingAttendeesRepository
import brc.studybuddy.input.MeetingAttendeeInput
import brc.studybuddy.model.MeetingAttendee
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(value = ["attendees"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AttendeesController {

    lateinit var meetingAttendeesRepository: MeetingAttendeesRepository

    @PutMapping
    fun save(@RequestBody input: MeetingAttendeeInput): Mono<MeetingAttendee> = Mono.just(input).map(MeetingAttendeeInput::toModel)

    @DeleteMapping
    fun deleteByUserId(id: Long): Mono<Void> = meetingAttendeesRepository.deleteMeetingAttendeeByUserId(id)

    //ToDO: addUserToMeeting, removeUserToMeeting

}