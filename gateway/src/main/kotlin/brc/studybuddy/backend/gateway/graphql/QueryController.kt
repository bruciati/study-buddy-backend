package brc.studybuddy.backend.gateway.graphql

import brc.studybuddy.backend.gateway.service.GroupsWebClient
import brc.studybuddy.backend.gateway.service.MeetingsWebClient
import brc.studybuddy.backend.gateway.service.UsersWebClient
import brc.studybuddy.model.Group
import brc.studybuddy.model.Meeting
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Controller
class QueryController {
    @Autowired
    private lateinit var usersWebClient: UsersWebClient

    @Autowired
    private lateinit var groupsWebClient: GroupsWebClient

    @Autowired
    private lateinit var meetingsWebClient: MeetingsWebClient


    // ------------------------------------------------------
    // --------------------- User Class ---------------------
    // ------------------------------------------------------

    @QueryMapping
    fun users(@Argument ids: Optional<List<Long>>): Flux<User> = usersWebClient.getUsers(ids)

    @QueryMapping
    fun userById(@Argument id: Long): Mono<User> = usersWebClient.getUser(id)

    @QueryMapping
    fun userByEmail(@Argument email: String): Mono<User> = usersWebClient.getUserByEmail(email)


    // ------------------------------------------------------
    // --------------------- Group Class --------------------
    // ------------------------------------------------------

    @QueryMapping
    fun groups(@Argument ids: Optional<List<Long>>): Flux<Group> = groupsWebClient.getGroups(ids)

    @QueryMapping
    fun groupById(@Argument id: Long): Mono<Group> = groupsWebClient.getGroup(id)

    @QueryMapping
    fun groupByTitle(@Argument title: String): Mono<Group> = groupsWebClient.getGroupByTitle(title)

    // ------------------------------------------------------
    // -------------------- Meeting Class -------------------
    // ------------------------------------------------------

    @QueryMapping
    fun meetingById(@Argument id: Long): Mono<Meeting> = meetingsWebClient.getMeeting(id)
}
