package brc.studybuddy.backend.gateway.graphql

import brc.studybuddy.backend.gateway.client.GroupsWebClient
import brc.studybuddy.backend.gateway.client.MeetingsWebClient
import brc.studybuddy.backend.gateway.client.UsersWebClient
import brc.studybuddy.input.GroupInput
import brc.studybuddy.input.MeetingInput
import brc.studybuddy.model.Group
import brc.studybuddy.model.Meeting
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

@Controller
class MutationController {
    @Autowired
    private lateinit var usersWebClient: UsersWebClient

    @Autowired
    private lateinit var groupsWebClient: GroupsWebClient

    @Autowired
    private lateinit var meetingsWebClient: MeetingsWebClient


    // ------------------------------------------------------
    // --------------------- Group Class --------------------
    // ------------------------------------------------------

    fun saveGroup(@Argument input: GroupInput): Mono<Group> = TODO()

    fun updateGroup(@Argument input: GroupInput): Mono<Group> = TODO()

    fun deleteGroup(@Argument id: Long): Mono<Boolean> = TODO()

    fun addGroupMember(@Argument groupId: Long, @Argument userId: Long): Mono<Boolean> = TODO()

    // ------------------------------------------------------
    // -------------------- Meeting Class -------------------
    // ------------------------------------------------------

    fun saveMeeting(@Argument input: MeetingInput): Mono<Meeting> = TODO()

    fun updateMeeting(@Argument input: MeetingInput): Mono<Meeting> = TODO()

    fun deleteMeeting(@Argument id: Long): Mono<Boolean> = TODO()

    fun addMeetingAttendee(@Argument meetingId: Long, @Argument userId: Long): Mono<Boolean> = TODO()
}
