package brc.studybuddy.backend.groups.service

import brc.studybuddy.input.GroupMemberInput
import brc.studybuddy.input.MeetingAttendeeInput
import brc.studybuddy.input.UserInput
import brc.studybuddy.model.GroupMember
import brc.studybuddy.model.MeetingAttendee
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class UsersClientService {
    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder
    private val webClient: WebClient by lazy(webClientBuilder.baseUrl("lb://users")::build)


    fun addUser(input: UserInput): Mono<User> = webClient.post()
        .uri("/users")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(User::class.java)

    fun addGroupMember(input: GroupMemberInput): Mono<GroupMember> = webClient.post()
        .uri("/members")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(GroupMember::class.java)

    fun addMeetingAttendee(input: MeetingAttendeeInput): Mono<MeetingAttendee> = webClient.post()
        .uri("/attendees")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(MeetingAttendee::class.java)


    fun updateUser(id: Long, input: UserInput): Mono<User> = webClient.put()
        .uri("/users/$id")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(User::class.java)

    fun updateGroupMember(id: Long, input: GroupMemberInput): Mono<GroupMember> = webClient.put()
        .uri("/members/$id")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(GroupMember::class.java)

    fun updateMeetingAttendee(id: Long, input: MeetingAttendeeInput): Mono<MeetingAttendee> = webClient.put()
        .uri("/attendees/$id")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(MeetingAttendee::class.java)


    fun deleteUser(id: Long): Mono<Void> = webClient.delete()
        .uri("/users/$id")
        .retrieve()
        .bodyToMono(Void::class.java)

    fun deleteGroupMember(id: Long): Mono<Void> = webClient.delete()
        .uri("/members/$id")
        .retrieve()
        .bodyToMono(Void::class.java)

    fun deleteMeetingAttendee(id: Long): Mono<Void> = webClient.delete()
        .uri("/attendees/$id")
        .retrieve()
        .bodyToMono(Void::class.java)
}
