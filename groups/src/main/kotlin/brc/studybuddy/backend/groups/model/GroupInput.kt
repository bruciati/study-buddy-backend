package brc.studybuddy.backend.groups.model

data class GroupInput(
    val owner: Long,
    val title: String,
    val description: String?
)
