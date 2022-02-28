package brc.studybuddy.backend.auth.model

sealed interface AuthResponse

data class AuthSuccess(
    val token: String
) : AuthResponse

data class AuthError(
    val status: Int,
    override val message: String
) : AuthResponse, Exception(message)
