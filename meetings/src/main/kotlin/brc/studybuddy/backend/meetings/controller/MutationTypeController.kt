package brc.studybuddy.backend.meetings.controller

import brc.studybuddy.backend.meetings.model.MeetingInput
import brc.studybuddy.backend.meetings.repository.MeetingsRepository
import brc.studybuddy.model.Meeting
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Controller
class MutationTypeController {

    @Autowired
    private lateinit var meetingsRepository: MeetingsRepository

    @MutationMapping
    fun addMeeting(@Argument input: MeetingInput): Mono<Meeting> =
        Mono.just(input.toMeeting())
            .flatMap{m -> meetingsRepository.save(m).thenReturn(m) }

    @MutationMapping
    fun updateMeeting(@Argument("id") meetingId: Long, @Argument input: MeetingInput): Mono<Meeting> =
        meetingsRepository.findById(meetingId).map(input:: updateMeeting).flatMap(meetingsRepository::save)

    @MutationMapping
    fun removeMeeting(@Argument("id") meetingId: Long) = meetingsRepository.deleteById(meetingId).thenReturn(true)
}