package brc.studybuddy.backend.users.repository

import brc.studybuddy.database.model.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UsersRepository : ReactiveCrudRepository<User, Long> {
    fun findByEmail(email: String): Mono<User>
}
