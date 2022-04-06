package brc.studybuddy.backend.meetings.repository

import brc.studybuddy.model.MeetingAttendee
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
sealed interface MeetingAttendeesRepository: ReactiveCrudRepository<MeetingAttendee, Long> {

    fun deleteMeetingAttendeeByUserId(id: Long) : Mono<Void>

}