package brc.studybuddy.backend.gateway.graphql.type

import brc.studybuddy.backend.gateway.service.UsersWebClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
@SchemaMapping(typeName = "User")
class UserTypeController {
    @Autowired
    private lateinit var usersWebClient: UsersWebClient



}
