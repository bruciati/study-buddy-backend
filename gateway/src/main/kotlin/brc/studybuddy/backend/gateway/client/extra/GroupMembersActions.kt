package brc.studybuddy.backend.gateway.client.extra

import brc.studybuddy.input.GroupMemberInput
import brc.studybuddy.model.GroupMember
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

internal interface GroupMembersActions {
    val webClient: WebClient


    fun saveGroupMember(input: GroupMemberInput): Mono<GroupMember> = webClient.post()
        .uri("/members")
        .bodyValue(input::toModel)
        .retrieve()
        .bodyToMono(GroupMember::class.java)


    fun deleteGroupMembersByUserId(id: Long): Mono<Void> = webClient.delete()
        .uri("/members/user/$id")
        .retrieve()
        .bodyToMono(Void::class.java)

    fun deleteGroupMembersByGroupId(id: Long): Mono<Void> = webClient.delete()
        .uri("/members/group/$id")
        .retrieve()
        .bodyToMono(Void::class.java)

    fun deleteGroupMemberByGroupIdAndUserId(groupId: Long, userId: Long): Mono<Void> = webClient.delete()
        .uri("/members/group/$groupId/user/$userId")
        .retrieve()
        .bodyToMono(Void::class.java)
}
