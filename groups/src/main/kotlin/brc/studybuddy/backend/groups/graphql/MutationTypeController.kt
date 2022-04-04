package brc.studybuddy.backend.groups.graphql

import brc.studybuddy.backend.groups.repository.GroupsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class MutationTypeController {
    @Autowired
    private lateinit var groupsRepository: GroupsRepository

}
