package brc.studybuddy.backend.auth.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

sealed interface AuthResponse

data class AuthSuccess(
    val accessToken: String,
    val refreshToken: String
) : AuthResponse