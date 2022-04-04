package brc.studybuddy.backend.users.repository

import brc.studybuddy.backend.users.model.GroupMember
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
sealed interface MembersRepository : ReactiveCrudRepository<GroupMember, Long> {

    fun findAllByGroupId(groupId: Long): Flux<GroupMember>

    fun findByGroupIdAndIsOwner(groupId: Long, isOwner: Boolean = true): Mono<GroupMember>

}
