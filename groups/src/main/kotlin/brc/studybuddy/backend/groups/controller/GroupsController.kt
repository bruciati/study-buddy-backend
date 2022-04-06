package brc.studybuddy.backend.groups.controller

import brc.studybuddy.backend.groups.repository.GroupsRepository
import brc.studybuddy.backend.groups.repository.MembersRepository
import brc.studybuddy.input.GroupInput
import brc.studybuddy.model.Group
import brc.studybuddy.model.GroupMember
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping(value = ["groups"], produces = [MediaType.APPLICATION_JSON_VALUE])
class GroupsController {
    @Autowired
    lateinit var groupsRepository: GroupsRepository

    @Autowired
    lateinit var membersRepository: MembersRepository


    @PostMapping
    fun save(@RequestBody input: GroupInput): Mono<Group> = Mono.just(input)
        .map(GroupInput::toModel)
        .flatMap(groupsRepository::save)


    @GetMapping // /groups?id=1&id=2...
    fun findAll(@RequestParam id: Optional<List<Long>>): Flux<Group> = Mono.justOrEmpty(id)
        .flatMapMany(groupsRepository::findAllById)
        .switchIfEmpty(groupsRepository.findAll())

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): Mono<Group> = groupsRepository.findById(id)

    @GetMapping("/title/{title}")
    fun findByTitle(@PathVariable title: String): Mono<Group> = groupsRepository.findByTitle(title)

    @GetMapping("/user/{id}")
    fun findAllByUserId(@PathVariable id: Long, @RequestParam("is_owner") isOwner: Optional<Boolean>): Flux<Group> =
        Mono.justOrEmpty(isOwner)
            .flatMapMany { b -> membersRepository.findAllByUserIdAndIsOwner(id, b) }
            .switchIfEmpty(membersRepository.findAllByUserId(id))
            .map(GroupMember::groupId)
            .collectList()
            .flatMapMany(groupsRepository::findAllById)


    @PutMapping("/{id}")
    fun updateById(@PathVariable id: Long, @RequestBody input: GroupInput): Mono<Group> =
        groupsRepository.findById(id)
            .map(input::updateModel)
            .flatMap(groupsRepository::save)

    @PutMapping("/title/{title}")
    fun updateByTitle(@PathVariable title: String, @RequestBody input: GroupInput): Mono<Group> =
        groupsRepository.findByTitle(title)
            .map(input::updateModel)
            .flatMap(groupsRepository::save)


    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): Mono<Boolean> = groupsRepository.deleteById(id)
        .thenReturn(true)
        .onErrorReturn(false)

    @DeleteMapping("/title/{title}")
    fun deleteByTitle(@PathVariable title: String): Mono<Boolean> = groupsRepository.deleteByTitle(title)
        .thenReturn(true)
        .onErrorReturn(false)

    @DeleteMapping("/user/{id}")
    fun deleteAllByUserIdAndIsOwnerTrue(@PathVariable id: Long): Mono<Boolean> =
        membersRepository.findAllByUserIdAndIsOwner(id)
            .map(GroupMember::groupId)
            .collectList()
            .flatMap(groupsRepository::deleteAllById)
            .thenReturn(true)
            .onErrorReturn(false)
}
