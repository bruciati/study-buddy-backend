package brc.studybuddy.backend.auth.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(
    @Id
    val id: Int,
    val email: String,
    val password: String
)