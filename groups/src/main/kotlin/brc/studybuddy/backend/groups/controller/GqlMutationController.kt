package brc.studybuddy.backend.groups.controller

import brc.studybuddy.backend.groups.repository.GroupsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class GqlMutationController {
    @Autowired
    private lateinit var groupsRepository: GroupsRepository

    // TODO Work-In-Progress
}