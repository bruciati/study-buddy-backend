package brc.studybuddy.backend.gateway.graphql

import brc.studybuddy.backend.gateway.client.GroupsWebClient
import brc.studybuddy.backend.gateway.client.MeetingsWebClient
import brc.studybuddy.backend.gateway.client.UsersWebClient
import brc.studybuddy.input.GroupInput
import brc.studybuddy.input.GroupMemberInput
import brc.studybuddy.input.MeetingInput
import brc.studybuddy.model.Group
import brc.studybuddy.model.Meeting
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestHeader
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

    @MutationMapping
    fun saveGroup(
        @RequestHeader("X-UserID") userId: Long,
        @Argument input: GroupInput
    ): Mono<Group> = groupsWebClient.saveGroup(input)
        .flatMap { g ->
            Mono.just(GroupMemberInput(g.id, userId, true))
                .flatMap { m ->
                    groupsWebClient.saveGroupMember(m)
                        .then(usersWebClient.saveGroupMember(m))
                        .onErrorResume { e ->
                            groupsWebClient.deleteGroupMember(m)
                                .then(Mono.error(e))
                        }
                }
                .onErrorResume { e ->
                    groupsWebClient.deleteGroup(g.id)
                        .then(Mono.error(e))
                }
                .thenReturn(g)
        }

    @MutationMapping
    fun updateGroup(@Argument id: Long, @Argument input: GroupInput): Mono<Group> =
        groupsWebClient.updateGroup(id, input)

    // TODO delete owner user (using auth token info)
    @MutationMapping
    fun deleteGroup(
        @RequestHeader("X-UserID") userId: Long,
        @Argument groupId: Long
    ): Mono<Boolean> = groupsWebClient.deleteGroup(groupId)

    @MutationMapping
    fun addGroupMember(@Argument groupId: Long, @Argument userId: Long): Mono<Boolean> =
        Mono.just(GroupMemberInput(groupId, userId, false))
            .flatMap { m ->
                groupsWebClient.saveGroupMember(m)
                    .then(usersWebClient.saveGroupMember(m))
                    .onErrorResume { e ->
                        groupsWebClient.deleteGroupMember(m)
                            .then(Mono.error(e))
                    }
                    .thenReturn(true)
            }

    @MutationMapping
    fun removeGroupMember(@Argument groupId: Long, @Argument userId: Long): Mono<Boolean> =
        Mono.just(GroupMemberInput(groupId, userId, false))
            .flatMap { m ->
                TODO()
            }


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
