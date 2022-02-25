package brc.studybuddy.backend.meetings.controller

import brc.studybuddy.backend.meetings.repository.MeetingsRepository
import brc.studybuddy.database.model.Meeting
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.repository.Query
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class GqlQueryController {
    @Autowired
    lateinit var meetingsRepository: MeetingsRepository

    @QueryMapping
    fun findMeetingsByGroupID(@Argument("group_id") groupId: Long) = meetingsRepository.findMeetingsByGroupId(groupId)

    @QueryMapping
    fun findMeetingsByHostId(@Argument("host_id") hostId: Long) = meetingsRepository.findMeetingsByHostId(hostId)

    @QueryMapping
    fun findMeetingsByLocation(@Argument location: String) = meetingsRepository.findMeetingsByLocation(location)

    @QueryMapping
    fun findMeetingsByDateTime(@Argument dateTime: Long) = meetingsRepository.findMeetingByDateTime(dateTime)

}
