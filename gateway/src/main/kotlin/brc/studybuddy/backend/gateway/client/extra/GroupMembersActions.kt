package brc.studybuddy.backend.gateway.client.extra

import brc.studybuddy.backend.gateway.client.MEMBERS_ENDPOINT
import brc.studybuddy.input.GroupMemberInput
import brc.studybuddy.model.GroupMember
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

internal interface GroupMembersActions {
    val webClient: WebClient


    fun saveGroupMember(input: GroupMemberInput): Mono<GroupMember> = webClient.post()
        .uri(MEMBERS_ENDPOINT)
        .bodyValue(input)
        .retrieve()
        .bodyToMono(GroupMember::class.java)


    fun deleteGroupMembersByUserId(id: Long): Mono<Boolean> = webClient.delete()
        .uri("$MEMBERS_ENDPOINT/user/$id")
        .retrieve()
        .bodyToMono(Boolean::class.java)

    fun deleteGroupMembersByGroupId(id: Long): Mono<Boolean> = webClient.delete()
        .uri("$MEMBERS_ENDPOINT/group/$id")
        .retrieve()
        .bodyToMono(Boolean::class.java)

    fun deleteGroupMember(input: GroupMemberInput): Mono<Boolean> = webClient.delete()
        .uri("$MEMBERS_ENDPOINT/group/${input.groupId}/user/${input.userId}")
        .retrieve()
        .bodyToMono(Boolean::class.java)
}
