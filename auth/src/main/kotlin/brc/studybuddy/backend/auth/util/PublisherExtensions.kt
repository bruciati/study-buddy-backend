package brc.studybuddy.backend.auth.util

import brc.studybuddy.backend.auth.models.ServiceError
import brc.studybuddy.backend.auth.models.ServiceResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/* TODO: Handle errors correctly */
fun <T> Mono<T>.toServiceResponse(): Mono<ServiceResponse<T>> =
    this.map { r -> ServiceResponse(true, r, null) }
        .onErrorResume { e -> Mono.just(ServiceResponse(false, null, ServiceError(500, e.message))) }

fun <T> Flux<T>.toServiceResponse(): Mono<ServiceResponse<List<T>>> =
    this.collectList()
        .map { r -> ServiceResponse(true, r, null) }
        .onErrorResume { e -> Mono.just(ServiceResponse(false, null, ServiceError(500, e.message))) }