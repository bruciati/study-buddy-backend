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


    fun updateGroup(id: Long, input: GroupInput): Mono<Group> = webClient.put()
        .uri("/groups/$id")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(Group::class.java)

    fun updateGroupMember(id: Long, input: GroupMemberInput): Mono<GroupMember> = webClient.put()
        .uri("/members/$id")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(GroupMember::class.java)


    fun deleteGroup(id: Long): Mono<Void> = webClient.delete()
        .uri("/groups/$id")
        .retrieve()
        .bodyToMono(Void::class.java)

    fun deleteGroupMember(id: Long): Mono<Void> = webClient.delete()
        .uri("/members/$id")
        .retrieve()
        .bodyToMono(Void::class.java)
}
