package brc.studybuddy.backend.meetings.controller

import brc.studybuddy.backend.meetings.repository.MeetingsRepository
import brc.studybuddy.database.model.Meeting
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class GqlMutationController {
    @Autowired
    lateinit var meetingsRepository: MeetingsRepository

    //@QueryMapping
    //fun createMeeting(@Argument meeting: Meeting)

}