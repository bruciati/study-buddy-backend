package brc.studybuddy.backend.auth.models

data class User(
    val id: Int,
    val email: String,
    val password: String
)