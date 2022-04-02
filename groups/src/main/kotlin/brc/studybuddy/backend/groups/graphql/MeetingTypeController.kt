package brc.studybuddy.backend.groups.graphql

import brc.studybuddy.model.Group
import brc.studybuddy.model.Meeting
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

@Controller
@SchemaMapping(typeName = "Meeting")
class MeetingTypeController {

    //@SchemaMapping(field = "id")
    //fun getFieldId(meeting: Meeting): Mono<Long> = Mono.just(meeting.id)

    //@SchemaMapping(field = "name")
    //fun getFieldName(meeting: Meeting): Mono<String> = Mono.just(meeting.name)

    @SchemaMapping(field = "group")
    fun getFieldGroup(meeting: Meeting): Mono<Group> = TODO() // TODO needed?

    //@SchemaMapping(field = "datetime")
    //fun getFieldDatetime(meeting: Meeting): Mono<Long> = Mono.just(meeting.dateTime)

    //@SchemaMapping(field = "type")
    //fun getFieldType(meeting: Meeting): Mono<Meeting.Type> = Mono.just(meeting.type)

    //@SchemaMapping(field = "location")
    //fun getFieldLocation(meeting: Meeting): Mono<String> = Mono.just(meeting.location)
}