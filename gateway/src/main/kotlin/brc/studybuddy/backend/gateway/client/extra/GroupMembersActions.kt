package brc.studybuddy.backend.gateway.client.extra

import brc.studybuddy.input.GroupMemberInput
import brc.studybuddy.model.GroupMember
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

internal interface GroupMembersActions {
    val webClient: WebClient


    fun saveGroupMember(input: GroupMemberInput): Mono<GroupMember> = webClient.post()
        .uri("/members")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(GroupMember::class.java)


    fun deleteGroupMembersByUserId(id: Long): Mono<Boolean> = webClient.delete()
        .uri("/members/user/$id")
        .retrieve()
        .bodyToMono(Boolean::class.java)

    fun deleteGroupMembersByGroupId(id: Long): Mono<Boolean> = webClient.delete()
        .uri("/members/group/$id")
        .retrieve()
        .bodyToMono(Boolean::class.java)

    fun deleteGroupMemberByGroupIdAndUserId(groupId: Long, userId: Long): Mono<Boolean> = webClient.delete()
        .uri("/members/group/$groupId/user/$userId")
        .retrieve()
        .bodyToMono(Boolean::class.java)
}
