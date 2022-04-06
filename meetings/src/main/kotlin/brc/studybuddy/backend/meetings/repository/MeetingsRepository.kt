package brc.studybuddy.backend.meetings.repository
import brc.studybuddy.model.Meeting
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
sealed interface MeetingsRepository : ReactiveCrudRepository<Meeting, Long> {

    fun findMeetingsByLocation(location: String) : Flux<Meeting>
}

