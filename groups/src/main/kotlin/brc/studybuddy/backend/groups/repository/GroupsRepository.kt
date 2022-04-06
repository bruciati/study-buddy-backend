package brc.studybuddy.backend.groups.repository

import brc.studybuddy.model.Group
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
sealed interface GroupsRepository : ReactiveCrudRepository<Group, Long> {
    fun findByTitle(title: String): Mono<Group>

    fun deleteByTitle(title: String): Mono<Void>
}
