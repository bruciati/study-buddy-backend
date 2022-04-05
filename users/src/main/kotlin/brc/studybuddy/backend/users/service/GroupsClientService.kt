package brc.studybuddy.backend.users.service

import brc.studybuddy.input.GroupInput
import brc.studybuddy.input.GroupMemberInput
import brc.studybuddy.model.Group
import brc.studybuddy.model.GroupMember
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class GroupsClientService {
    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder
    private val webClient: WebClient by lazy(webClientBuilder.baseUrl("lb://groups")::build)


    fun addGroup(input: GroupInput): Mono<Group> = webClient.post()
        .uri("/groups")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(Group::class.java)

    fun addGroupMember(input: GroupMemberInput): Mono<GroupMember> = webClient.post()
        .uri("/members")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(GroupMember::class.java)


    fun updateGroup(groupId: Long, input: GroupInput): Mono<Group> = webClient.put()
        .uri("/groups/${groupId}")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(Group::class.java)

    fun updateGroupMember(userId: Long, input: GroupMemberInput): Mono<GroupMember> = webClient.put()
        .uri("/members/${userId}")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(GroupMember::class.java)


    fun deleteGroup(groupId: Long): Mono<Void> = webClient.delete()
        .uri("/groups/$groupId")
        .retrieve()
        .bodyToMono(Void::class.java)

    fun deleteGroupMember(userId: Long): Mono<Void> = webClient.delete()
        .uri("/members/$userId")
        .retrieve()
        .bodyToMono(Void::class.java)
}
