package brc.studybuddy.backend.meetings.repository

import brc.studybuddy.model.MeetingAttendee
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
sealed interface MeetingAttendeesRepository: ReactiveCrudRepository<MeetingAttendee, Long> {

    fun deleteAllByUserId(id: Long) : Mono<Void>
    fun deleteByUserIdAndAndMeetingId(userId: Long, meetingId: Long) : Mono<Void>
    fun deleteAllByMeetingId(meetingId: Long) : Mono<Void>
    fun findAllByUserId(id: Long) : Flux<MeetingAttendee>
    fun findAllByUserIdAndIsHost(id:Long, isHost : Boolean = true): Flux<MeetingAttendee>

}