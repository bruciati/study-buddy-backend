package brc.studybuddy.backend.groups.graphql

import brc.studybuddy.backend.groups.repository.GroupsRepository
import brc.studybuddy.model.Group
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class QueryTypeController {
    @Autowired
    private lateinit var groupsRepository: GroupsRepository

    @QueryMapping
    fun groups(@Argument ids: List<Long>?): Flux<Group> = Mono.justOrEmpty(ids)
        .flatMapMany(groupsRepository::findAllById)
        .switchIfEmpty(groupsRepository.findAll())

    @QueryMapping
    fun groupById(@Argument id: Long): Mono<Group> = groupsRepository.findById(id)

    @QueryMapping
    fun groupByTitle(@Argument title: String): Mono<Group> = groupsRepository.findByTitle(title)
}
