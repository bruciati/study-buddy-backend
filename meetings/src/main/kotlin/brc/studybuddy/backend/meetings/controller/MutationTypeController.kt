package brc.studybuddy.backend.meetings.controller

import brc.studybuddy.backend.meetings.repository.MeetingsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.client.WebClient

@Controller
class MutationTypeController {

    @Autowired
    private lateinit var meetingsRepository: MeetingsRepository

}