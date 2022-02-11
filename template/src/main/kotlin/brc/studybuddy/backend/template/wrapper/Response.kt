package brc.studybuddy.backend.template.wrapper

data class Response<T>(
    val ok: Boolean,
    val data: T?,
    val error: Error? = null
) {
    data class Error(
        val value: Int,
        val message: String?
    )
}
