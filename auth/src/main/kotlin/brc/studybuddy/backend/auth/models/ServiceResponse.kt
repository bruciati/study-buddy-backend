package brc.studybuddy.backend.auth.models

data class ServiceResponse<T>(
        val status: Boolean,
        val data: T?,
        val error: Exception?
    )