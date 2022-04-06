package brc.studybuddy.backend.meetings.controller

import brc.studybuddy.backend.meetings.repository.MeetingsRepository
import brc.studybuddy.input.MeetingInput
import brc.studybuddy.model.Meeting
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.Optional

@RestController
@RequestMapping(value = ["meetings"], produces = [MediaType.APPLICATION_JSON_VALUE])
class MeetingsController {
    @Autowired
    lateinit var meetingsRepository: MeetingsRepository

    @GetMapping
    fun getAllMeetings(): Flux<Meeting> = meetingsRepository.findAll()

    @GetMapping("/{id}")
    fun getMeetingById(@PathVariable("id") id: Long) = meetingsRepository.findById(id)

    @GetMapping
    fun getMeetingByIds(@RequestParam id: Optional<List<Long>>): Flux<Meeting> =
        Mono.justOrEmpty(id).flatMapMany(meetingsRepository::findAllById).switchIfEmpty(meetingsRepository.findAll())

    @GetMapping("/location/{location}")
    fun getMeetingByLocation(@PathVariable("location") location: String) =
        meetingsRepository.findMeetingsByLocation(location)

    // ToDO: recuperare tutti meeting di un utente, cancellare tutti i meeting di un utente(solo se host).
    @GetMapping("/user/{id}")
    fun getMeetingsByUserId(id: Long): Flux<Meeting> = TODO()

    @PostMapping("/meetings")
    fun addMeeting(input: MeetingInput): Mono<Meeting> = Mono.just(input.toModel()).flatMap(meetingsRepository::save)

    @PutMapping("/{id}")
    fun updateMeetingById(@PathVariable id: Long, @RequestBody input: MeetingInput): Mono<Meeting> =
        meetingsRepository.findById(id).map(input::updateModel).flatMap(meetingsRepository::save)

    @DeleteMapping
    fun deleteMeetingById(@PathVariable id: Long) : Mono<Void> = meetingsRepository.deleteById(id)



}