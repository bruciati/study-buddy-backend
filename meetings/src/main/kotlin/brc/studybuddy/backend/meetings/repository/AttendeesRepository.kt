package brc.studybuddy.backend.meetings.repository
import brc.studybuddy.backend.meetings.model.Attendees
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface AttendeesRepository : ReactiveCrudRepository<Attendees, Long>{
    fun findAllByUserId(user_id:Long): Flux<Attendees>
    fun findAllByMeetingId(meeting_id: Long): Flux<Attendees>
}