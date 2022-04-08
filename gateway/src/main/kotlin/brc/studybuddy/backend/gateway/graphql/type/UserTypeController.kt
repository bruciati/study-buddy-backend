package brc.studybuddy.backend.gateway.graphql.type

import brc.studybuddy.backend.gateway.service.GroupsWebClient
import brc.studybuddy.backend.gateway.service.MeetingsWebClient
import brc.studybuddy.backend.gateway.service.UsersWebClient
import brc.studybuddy.model.Group
import brc.studybuddy.model.Meeting
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import java.util.*

@Controller
@SchemaMapping(typeName = "User")
class UserTypeController {
    @Autowired
    private lateinit var groupsWebClient: GroupsWebClient

    @Autowired
    private lateinit var meetingsWebClient: MeetingsWebClient


    @SchemaMapping(field = "groups")
    fun getFieldGroups(user: User): Flux<Group> =
        groupsWebClient.getGroupsByUserId(user.id, Optional.empty())

    @SchemaMapping(field = "meetings")
    fun getFieldMeetings(user: User): Flux<Meeting> =
        meetingsWebClient.getMeetingsByUserId(user.id, Optional.empty())
}
