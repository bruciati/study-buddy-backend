package brc.studybuddy.backend.auth.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import brc.studybuddy.model.User

// See: https://docs.spring.io/spring-data/r2dbc/docs/current/reference/html/#r2dbc.repositories.queries
@Repository
interface UserRepository : ReactiveCrudRepository<User, UInt> {
    fun findFirstByEmailAndLoginValue(email: String, password: String): Mono<User>
}