package brc.studybuddy.backend.auth.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/* Main response wrapper */
data class FacebookResponse(val data: FacebookResponseData)

/* Instruct Jackson with the classes that can use to deserialize the interface */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes(
    JsonSubTypes.Type(value = FacebookSuccess::class),
    JsonSubTypes.Type(value = FacebookError::class)
)
interface FacebookResponseData

/* Successful Facebook response */
data class FacebookSuccess(
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

/* Error Facebook response */
data class FacebookError(
    val error: Error,
    @JsonProperty("is_valid")
    val isValid: Boolean,
) : FacebookResponseData {
    data class Error(val code: Int, val message: String)
}


