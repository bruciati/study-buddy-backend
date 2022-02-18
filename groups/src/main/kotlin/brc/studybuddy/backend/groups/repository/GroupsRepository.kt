package brc.studybuddy.backend.groups.repository

import brc.studybuddy.database.model.Group
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface GroupsRepository : ReactiveCrudRepository<Group, Long> {
    fun findByTitle(title: String): Mono<Group>
}
