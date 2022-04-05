package brc.studybuddy.backend.users.repository

import brc.studybuddy.model.MeetingAttendee
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
sealed interface AttendeesRepository : ReactiveCrudRepository<MeetingAttendee, Long>
