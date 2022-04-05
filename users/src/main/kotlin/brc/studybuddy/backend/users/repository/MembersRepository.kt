package brc.studybuddy.backend.users.repository

import brc.studybuddy.model.GroupMember
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
sealed interface MembersRepository : ReactiveCrudRepository<GroupMember, Long>
