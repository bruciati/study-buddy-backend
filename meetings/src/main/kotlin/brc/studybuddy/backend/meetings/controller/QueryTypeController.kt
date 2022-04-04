package brc.studybuddy.backend.meetings.controller

import brc.studybuddy.backend.meetings.repository.MeetingsRepository
import brc.studybuddy.model.Meeting
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class QueryTypeController {
    @Autowired
    lateinit var meetingsRepository: MeetingsRepository
    

    @QueryMapping
    fun meetingById(@Argument("id") meetingId: Long): Mono<Meeting> = meetingsRepository.findById(meetingId)

    @QueryMapping
    fun meetings(@Argument ids: List<Long>?): Flux<Meeting> =
        Mono.justOrEmpty(ids).flatMapMany(meetingsRepository::findAllById).switchIfEmpty(meetingsRepository.findAll())

    @QueryMapping
    fun meetingsByGroupId(@Argument("id") groupId: Long): Flux<Meeting> = meetingsRepository.findAllByGroupId(groupId)

    @QueryMapping
    fun meetingsByLocation(@Argument location: String): Flux<Meeting> = meetingsRepository.findAllByLocation(location)

}
