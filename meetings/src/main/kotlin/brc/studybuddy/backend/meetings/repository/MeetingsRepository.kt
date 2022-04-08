package brc.studybuddy.backend.meetings.repository

import brc.studybuddy.model.Meeting
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
sealed interface MeetingsRepository : ReactiveCrudRepository<Meeting, Long> {
    fun findAllByLocation(location: String): Flux<Meeting>
    fun findByGroupId(id: Long): Flux<Meeting>
}

