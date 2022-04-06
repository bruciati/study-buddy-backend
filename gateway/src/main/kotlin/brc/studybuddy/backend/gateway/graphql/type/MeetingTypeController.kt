package brc.studybuddy.backend.gateway.graphql.type

import brc.studybuddy.backend.gateway.service.MeetingsWebClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
@SchemaMapping(typeName = "Meeting")
class MeetingTypeController {
    @Autowired
    private lateinit var meetingsWebClient: MeetingsWebClient



}
