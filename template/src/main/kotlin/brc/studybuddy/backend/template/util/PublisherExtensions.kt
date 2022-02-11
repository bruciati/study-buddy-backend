package brc.studybuddy.backend.template.util

import brc.studybuddy.backend.template.wrapper.Response
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/* TODO: Handle errors correctly */
fun <T> Mono<T>.toServiceResponse(): Mono<Response<T>> =
    this.map { r -> Response(true, r, null) }
        .onErrorResume { e -> Mono.just(Response(false, null, Response.Error(500, e.message))) }

fun <T> Flux<T>.toServiceResponse(): Mono<Response<List<T>>> =
    this.collectList()
        .map { r -> Response(true, r, null) }
        .onErrorResume { e -> Mono.just(Response(false, null, Response.Error(500, e.message))) }
