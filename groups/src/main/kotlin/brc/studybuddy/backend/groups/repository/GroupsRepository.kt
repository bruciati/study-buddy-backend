package brc.studybuddy.backend.groups.repository

import brc.studybuddy.model.Group
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
sealed interface GroupsRepository : ReactiveCrudRepository<Group, Long>
