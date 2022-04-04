package brc.studybuddy.backend.users.graphql

import brc.studybuddy.backend.users.model.GroupMember
import brc.studybuddy.backend.users.model.MeetingAttendee
import brc.studybuddy.backend.users.repository.AttendeesRepository
import brc.studybuddy.backend.users.repository.MembersRepository
import brc.studybuddy.backend.users.repository.UsersRepository
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class QueryTypeController {
    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var membersRepository: MembersRepository

    @Autowired
    private lateinit var attendeesRepository: AttendeesRepository


    @QueryMapping
    fun users(@Argument ids: List<Long>?): Flux<User> = Mono.justOrEmpty(ids)
        .flatMapMany(usersRepository::findAllById)
        .switchIfEmpty(usersRepository.findAll())

    @QueryMapping
    fun usersByGroupId(@Argument id: Long): Flux<User> = membersRepository.findAllByGroupId(id)
        .map(GroupMember::userId)
        .collectList()
        .flatMapMany(usersRepository::findAllById)

    @QueryMapping
    fun usersByMeetingId(@Argument id: Long): Flux<User> = attendeesRepository.findAllByMeetingId(id)
        .map(MeetingAttendee::userId)
        .collectList()
        .flatMapMany(usersRepository::findAllById)


    @QueryMapping
    fun userById(@Argument id: Long): Mono<User> = usersRepository.findById(id)

    @QueryMapping
    fun userByEmail(@Argument email: String): Mono<User> = usersRepository.findByEmail(email)

    @QueryMapping
    fun ownerByGroupId(@Argument id: Long): Mono<User> = membersRepository.findByGroupIdAndIsOwner(id)
        .map(GroupMember::userId)
        .flatMap(usersRepository::findById)

    @QueryMapping
    fun hostByMeetingId(@Argument id: Long): Mono<User> = attendeesRepository.findByMeetingIdAndIsHost(id)
        .map(MeetingAttendee::userId)
        .flatMap(usersRepository::findById)
}
