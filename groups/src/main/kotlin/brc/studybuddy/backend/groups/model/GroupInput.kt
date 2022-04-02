package brc.studybuddy.backend.groups.model

import brc.studybuddy.model.Group

data class GroupInput(
    val owner: Long? = null, // User#ID

    val title: String? = null,

    val description: String? = null
) {
    fun toGroupMember(groupId: Long, isOwner: Boolean = false): GroupMember =
        GroupMember(groupId, this.owner!!, isOwner)

    fun toGroup(): Group = Group(
        title = this.title!!,
        description = this.description
    )

    fun updateGroup(group: Group): Group = Group(
        group.id,
        this.title ?: group.title,
        this.description ?: group.description
    )
}
