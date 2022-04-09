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
import org.springframework.graphql.data.method.annotation.MutationMapping
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

    // TODO save owner user (using auth token info)
    @MutationMapping
    fun saveGroup(@Argument input: GroupInput): Mono<Group> = groupsWebClient.saveGroup(input)

    @MutationMapping
    fun updateGroup(@Argument id: Long, @Argument input: GroupInput): Mono<Group> =
        groupsWebClient.updateGroup(id, input)

    // TODO delete owner user (using auth token info)
    @MutationMapping
    fun deleteGroup(@Argument id: Long): Mono<Boolean> = groupsWebClient.deleteGroup(id)

    @MutationMapping
    fun addGroupMember(@Argument groupId: Long, @Argument userId: Long): Mono<Boolean> = TODO()

    @MutationMapping
    fun removeGroupMember(@Argument groupId: Long, @Argument userId: Long): Mono<Boolean> = TODO()


    // ------------------------------------------------------
    // -------------------- Meeting Class -------------------
    // ------------------------------------------------------

    // TODO save host user (using auth token info)
    @MutationMapping
    fun saveMeeting(@Argument input: MeetingInput): Mono<Meeting> = meetingsWebClient.saveMeeting(input)

    @MutationMapping
    fun updateMeeting(@Argument id: Long, @Argument input: MeetingInput): Mono<Meeting> =
        meetingsWebClient.updateMeeting(id, input)

    // TODO delete host user (using auth token info)
    @MutationMapping
    fun deleteMeeting(@Argument id: Long): Mono<Boolean> = meetingsWebClient.deleteMeeting(id)

    @MutationMapping
    fun addMeetingAttendee(@Argument meetingId: Long, @Argument userId: Long): Mono<Boolean> = TODO()

    @MutationMapping
    fun removeMeetingAttendee(@Argument meetingId: Long, @Argument userId: Long): Mono<Boolean> = TODO()
}
