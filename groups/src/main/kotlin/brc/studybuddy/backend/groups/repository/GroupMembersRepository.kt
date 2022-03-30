package brc.studybuddy.backend.groups.repository

import brc.studybuddy.backend.groups.model.GroupMember
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
sealed interface GroupMembersRepository : ReactiveCrudRepository<GroupMember, Long> {
    // MONOs
    fun findByGroupIdAndIsOwner(groupId: Long, isOwner: Boolean = true): Mono<GroupMember>

    // FLUXes
    fun findAllByGroupId(groupId: Long): Flux<GroupMember>

    fun findAllByUserId(userId: Long): Flux<GroupMember>

    fun findAllByUserIdAndIsOwner(userId: Long, isOwner: Boolean = true): Flux<GroupMember>
}
