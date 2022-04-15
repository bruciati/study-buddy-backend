package brc.studybuddy.backend.auth.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes(
    JsonSubTypes.Type(value = Success::class, name = "data"),
    JsonSubTypes.Type(value = Error::class, name = "data")
)
interface FacebookResponseData


data class FacebookResponse(
    val data: FacebookResponseData
)

data class Success(
    @JsonProperty("app_id")
    val appId: Long,
    val type: String,
    val application: String,
    @JsonProperty("expires_at")
    val expiresAt: Long,
    @JsonProperty("is_valid")
    val isValid: Boolean,
    @JsonProperty("user_id")
    val userId: Long
) : FacebookResponseData

data class Error(
    val error: Error,
    @JsonProperty("is_valid")
    val isValid: Boolean,
) : FacebookResponseData {
    data class Error(val code: Int, val message: String)
}


