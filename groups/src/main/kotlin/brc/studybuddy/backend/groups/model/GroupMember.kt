package brc.studybuddy.backend.groups.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("members")
data class GroupMember(
    @Id
    @Column("group_id")
    @JsonProperty(value = "group_id", defaultValue = "-1")
    val groupId: Long = -1,

    @Column("user_id")
    @JsonProperty(value = "user_id", defaultValue = "-1")
    val userId: Long = -1,

    @Column("is_owner")
    @JsonProperty(value = "is_owner", defaultValue = "false")
    val isOwner: Boolean = false
)
