package brc.studybuddy.backend.groups.model

data class GroupInput(
    val owner: Long? = null, // User#ID

    val title: String? = null,

    val description: String? = null
)
