package brc.studybuddy.backend.gateway.graphql

import brc.studybuddy.backend.gateway.service.GroupsWebClient
import brc.studybuddy.backend.gateway.service.MeetingsWebClient
import brc.studybuddy.backend.gateway.service.UsersWebClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class QueryController {
    @Autowired
    private lateinit var usersWebClient: UsersWebClient

    @Autowired
    private lateinit var groupsWebClient: GroupsWebClient

    @Autowired
    private lateinit var meetingsWebClient: MeetingsWebClient



}
