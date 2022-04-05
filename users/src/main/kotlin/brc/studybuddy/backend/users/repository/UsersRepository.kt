package brc.studybuddy.backend.users.repository

import brc.studybuddy.model.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
sealed interface UsersRepository : ReactiveCrudRepository<User, Long>
