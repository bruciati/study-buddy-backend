package brc.studybuddy.backend.auth.models

data class ServiceResponse<T>(
    val status: Boolean,
    val data: T?,
    val error: ServiceError?
)

data class ServiceError(
    val code: Int,
    val message: String?,
)
