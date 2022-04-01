package brc.studybuddy.backend.groups.graphql

import brc.studybuddy.backend.groups.repository.GroupMembersRepository
import brc.studybuddy.backend.groups.repository.GroupsRepository
import brc.studybuddy.model.Group
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux

@Controller
@SchemaMapping(typeName = "User")
class UserTypeController {
    @Autowired
    private lateinit var groupsRepository: GroupsRepository

    @Autowired
    private lateinit var groupMembersRepository: GroupMembersRepository

    //@SchemaMapping(field = "id")
    //fun getFieldId(user: User): Mono<Long> = Mono.just(user.id)

    //@SchemaMapping(field = "email")
    //fun getFieldEmail(user: User): Mono<String> = Mono.just(user.email)

    @SchemaMapping(field = "groups")
    fun getFieldGroups(user: User): Flux<Group> = groupMembersRepository.findAllByUserId(user.id)
        .map { m -> m.groupId }
        .collectList()
        .flatMapMany(groupsRepository::findAllById)
}
