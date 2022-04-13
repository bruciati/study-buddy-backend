package brc.studybuddy.backend.auth.model

data class FacebookResponse (
    val data: Data
)

data class Data (
    val appId: Int,
    val type: String,
    val application: String,
    val expiresAt: Long,
    val isValid: Boolean,
    val issuedAt: Long,
    val userId: Long
)


