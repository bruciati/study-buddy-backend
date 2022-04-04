package brc.studybuddy.backend.users.model

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("attendees")
data class MeetingAttendee(
    @Column("user_id")
    val userId: Long = 0,

    @Column("meeting_id")
    val meetingId: Long = 0,

    @Column("is_host")
    val isHost: Boolean = false,
)
