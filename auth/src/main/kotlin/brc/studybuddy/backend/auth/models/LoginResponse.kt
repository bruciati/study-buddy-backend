package brc.studybuddy.backend.auth.models

data class LoginResponse(
        val status: Boolean,
        val token: String
    )
