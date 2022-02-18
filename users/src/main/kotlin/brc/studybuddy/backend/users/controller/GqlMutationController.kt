package brc.studybuddy.backend.users.controller

import brc.studybuddy.backend.users.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class GqlMutationController {
    @Autowired
    private lateinit var usersRepository: UsersRepository
    
    // TODO Work-In-Progress
}