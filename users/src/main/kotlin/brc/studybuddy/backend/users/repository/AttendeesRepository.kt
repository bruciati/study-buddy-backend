package brc.studybuddy.backend.users.repository

import brc.studybuddy.backend.users.model.MeetingAttendee
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
sealed interface AttendeesRepository : ReactiveCrudRepository<MeetingAttendee, Long> {

    fun findAllByMeetingId(groupId: Long): Flux<MeetingAttendee>

    fun findByMeetingIdAndIsHost(groupId: Long, isHost: Boolean = true): Mono<MeetingAttendee>

}

