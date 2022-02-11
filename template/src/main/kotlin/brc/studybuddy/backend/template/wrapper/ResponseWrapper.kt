package brc.studybuddy.backend.template.wrapper

import brc.studybuddy.backend.template.wrapper.util.toServiceResponse
import org.springframework.core.MethodParameter
import org.springframework.http.codec.HttpMessageWriter
import org.springframework.web.reactive.HandlerResult
import org.springframework.web.reactive.accept.RequestedContentTypeResolver
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class ResponseWrapper(writers: List<HttpMessageWriter<*>>, resolver: RequestedContentTypeResolver) :
    ResponseBodyResultHandler(writers, resolver) {

    override fun supports(result: HandlerResult): Boolean =
        (result.returnType.resolve() === Mono::class.java)
                || (result.returnType.resolve() === Flux::class.java)

    override fun handleResult(exchange: ServerWebExchange, result: HandlerResult): Mono<Void> {
        val body = when (result.returnValue) {
            is Mono<*> -> (result.returnValue as Mono<*>).toServiceResponse()
            is Flux<*> -> (result.returnValue as Flux<*>).toServiceResponse()
            else -> throw RuntimeException("The \"body\" should be Mono<*> or Flux<*>!")
        }

        return writeBody(body, param, exchange)
    }

    /* TODO Better work-around for the "param" static variable (too long name?) */
    private fun uselessMethodForParamsSomeoneHelpsMeInOrderToRemoveThis(): Mono<Any>? = null

    companion object {
        private val param: MethodParameter = MethodParameter(
            ResponseWrapper::class.java
                .getDeclaredMethod("uselessMethodForParamsSomeoneHelpsMeInOrderToRemoveThis"), -1
        )
    }
}
