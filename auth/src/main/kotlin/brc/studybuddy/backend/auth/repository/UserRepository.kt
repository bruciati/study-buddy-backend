package brc.studybuddy.backend.auth.repository

import brc.studybuddy.database.model.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

// See: https://docs.spring.io/spring-data/r2dbc/docs/current/reference/html/#r2dbc.repositories.queries
@Repository
interface UserRepository : ReactiveCrudRepository<User, Long> {
    fun findFirstByEmailAndLoginValue(email: String, password: String): Mono<User>
}