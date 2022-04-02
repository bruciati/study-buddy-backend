package brc.studybuddy.backend.groups.graphql

import brc.studybuddy.backend.groups.model.GroupInput
import brc.studybuddy.backend.groups.model.GroupMember
import brc.studybuddy.backend.groups.repository.GroupMembersRepository
import brc.studybuddy.backend.groups.repository.GroupsRepository
import brc.studybuddy.model.Group
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Controller
class MutationTypeController {
    @Autowired
    private lateinit var groupsRepository: GroupsRepository

    @Autowired
    private lateinit var groupMembersRepository: GroupMembersRepository

    // NOTE it won't check if the user exists
    @Transactional
    @MutationMapping
    fun addGroup(
        @Argument input: GroupInput
    ): Mono<Group> = Mono.just(input.toGroup())
        .flatMap(groupsRepository::save)
        .flatMap { g ->
            val member = input.toGroupMember(g.id, true)
            groupMembersRepository.save(member).thenReturn(g)
        }
        .onErrorMap { e ->
            when (e) {
                is NullPointerException -> Exception("All required parameters (\"owner\", \"title\") must be defined!")
                is DataIntegrityViolationException -> Exception("A group with the given title already exists!")
                else -> e
            }
        }

    // NOTE owner change will not be implemented
    @MutationMapping
    fun updateGroup(@Argument id: Long, @Argument input: GroupInput): Mono<Group> = groupsRepository.findById(id)
        .map(input::updateGroup)
        .flatMap(groupsRepository::save)

    // TODO delete meetings
    @Transactional
    @MutationMapping
    fun deleteGroup(@Argument id: Long): Mono<Boolean> = groupMembersRepository.deleteAllByGroupId(id)
        .then(groupsRepository.deleteById(id))
        .thenReturn(true) // Can not be 'false'
}
