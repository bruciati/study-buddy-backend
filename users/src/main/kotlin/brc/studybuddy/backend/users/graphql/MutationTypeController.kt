package brc.studybuddy.backend.users.graphql

import brc.studybuddy.backend.users.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class MutationTypeController {
    @Autowired
    private lateinit var usersRepository: UsersRepository
    
    // TODO Work-In-Progress
}