package brc.studybuddy.backend.meetings.controller

import brc.studybuddy.backend.meetings.model.Attendees
import brc.studybuddy.backend.meetings.repository.AttendeesRepository
import brc.studybuddy.backend.meetings.repository.MeetingsRepository
import brc.studybuddy.model.Meeting
import brc.studybuddy.model.User
import brc.studybuddy.webclient.extension.graphQlQuery
import brc.studybuddy.webclient.extension.graphQlToFlux
import brc.studybuddy.webclient.extension.graphQlToMono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.client.WebClient
import javax.xml.stream.Location

@Controller
class QueryTypeController {
    @Autowired
    lateinit var meetingsRepository: MeetingsRepository

    @Autowired
    lateinit var attendeesRepository: AttendeesRepository

    @Autowired
    private lateinit var webClientBuilder : WebClient.Builder

    private val usersWebClient: WebClient by lazy { webClientBuilder.baseUrl("lb://users/users").build()}

    @QueryMapping
    fun meetingsByGroupID(@Argument("group_id") groupId: Long) = meetingsRepository.findAllByGroupId(groupId)

    @QueryMapping
    fun meetingsByUserId(@Argument("user_id") userId: Long) = attendeesRepository.findAllByUserId(userId)
        .map{a -> a.meetingId}
        .collectList()
        .flatMapMany{a -> meetingsRepository.findAllById(a)}

    @QueryMapping
    fun meetingByLocation(@Argument location: String) = meetingsRepository.findAllByLocation(location)

    @QueryMapping
    fun attendeesByMeetingId(@Argument("meeting_id") meetingId: Long) = attendeesRepository.findAllByMeetingId(meetingId)

    @QueryMapping
    fun attendeesByUserId(@Argument("user_id") userId: Long) = attendeesRepository.findAllByUserId(userId)

    @QueryMapping
    fun userInAttendsByMeetingID(@Argument("meeting_id") meetingId: Long) = attendeesRepository.findAllByMeetingId(meetingId)
        .map {a -> a.userId}
        .collectList()
        .flatMapMany{ a -> usersWebClient.post()
                            .graphQlQuery("userById(id: ${a}{id email}")
                            .retrieve()
                            .graphQlToFlux(User::class.java)
        }

    @QueryMapping
    fun hostAttendeesByMeetingID(@Argument("meeting_id") meetingId: Long) =  attendeesRepository.findAllByMeetingId(meetingId)
        .filter{a -> a.isHost}
        .map{a -> a.userId}
        .flatMap{ a -> usersWebClient.post()
            .graphQlQuery("userById(id: ${a}{id email}")
            .retrieve()
            .graphQlToMono(User::class.java)
        }
}
