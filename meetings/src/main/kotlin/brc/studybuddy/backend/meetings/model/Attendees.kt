package brc.studybuddy.backend.meetings.model
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("attendees")
data class Attendees(
    @Id
    @JsonProperty(value = "user_id", defaultValue = "-1")
    val userId: Long = -1,

    @Column("group_id")
    @JsonProperty(value = "meeting_id", defaultValue = "-1")
    val meetingId: Long = -1,

    @Column("is_host")
    @JsonProperty(value = "is_host", defaultValue = "false")
    val isHost: Boolean = false,
    )

