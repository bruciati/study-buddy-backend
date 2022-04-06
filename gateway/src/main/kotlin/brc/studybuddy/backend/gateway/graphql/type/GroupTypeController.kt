package brc.studybuddy.backend.gateway.graphql.type

import brc.studybuddy.backend.gateway.service.GroupsWebClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
@SchemaMapping(typeName = "Group")
class GroupTypeController {
    @Autowired
    private lateinit var groupsWebClient: GroupsWebClient



}
