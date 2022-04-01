package brc.studybuddy.backend.groups.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("members")
data class GroupMember(
    @Column("group_id")
    val groupId: Long = 0,

    @Column("user_id")
    val userId: Long = 0,

    @Column("is_owner")
    val isOwner: Boolean = false
)
