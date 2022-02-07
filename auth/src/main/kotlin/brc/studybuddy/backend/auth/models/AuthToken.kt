package brc.studybuddy.backend.auth.models

data class AuthToken(
    val token: String,
    val expiration: UInt
)
