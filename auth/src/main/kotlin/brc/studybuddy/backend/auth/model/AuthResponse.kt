package brc.studybuddy.backend.auth.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

sealed interface AuthResponse

data class AuthSuccess(
    val accessToken: String,
    val refreshToken: String
) : AuthResponse

@JsonIgnoreProperties(value = ["stackTrace", "cause", "suppressed", "localizedMessage"])
data class AuthError(
    val status: Int,
    override val message: String
) : AuthResponse, Exception(message)
