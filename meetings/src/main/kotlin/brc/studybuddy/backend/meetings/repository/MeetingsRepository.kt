package brc.studybuddy.backend.meetings.repository

import brc.studybuddy.database.model.Meeting
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.math.BigInteger

@Repository
interface MeetingsRepository : ReactiveCrudRepository<Meeting, Long> {
    fun findMeetingsByGroupId(groupId: Long) : Flux<Meeting>
    fun findMeetingsByHostId(hostId: Long) : Flux<Meeting>
    fun findMeetingsByLocation(location: String) : Flux<Meeting>
    fun findMeetingByDateTime(dateTime: Long) : Flux<Meeting>


}

