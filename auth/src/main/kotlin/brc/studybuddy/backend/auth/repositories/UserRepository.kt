package brc.studybuddy.backend.auth.repositories

import brc.studybuddy.backend.auth.models.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : ReactiveCrudRepository<User, Int>