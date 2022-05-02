package brc.studybuddy.backend.auth.model

sealed interface AuthResponse

data class AuthSuccess(
    val accessToken: String,
    val refreshToken: String
) : AuthResponse

data class AuthError(
    val status: Int,
    override val message: String
) : AuthResponse, Exception(message)
