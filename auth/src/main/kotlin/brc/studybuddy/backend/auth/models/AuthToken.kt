package brc.studybuddy.backend.auth.models

// TODO: Refactor into a JWT token
data class AuthToken(
    val token: String,
    val expiration: UInt
)
