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
        val body = when (val value = result.returnValue) {
            is Mono<*> -> value.toServiceResponse()
            is Flux<*> -> value.toServiceResponse()
            else -> throw RuntimeException("The \"body\" should be Mono<*> or Flux<*>!")
        }

        return writeBody(body, param, exchange)
    }

    // TODO Better work-around for the "param" static variable
    companion object {
        private val param: MethodParameter = MethodParameter(
            ResponseWrapper::class.java.getDeclaredMethod("methodForParamsDoesAnyoneKnowHowToRemoveIt"),
            -1
        )

        @JvmStatic
        private fun methodForParamsDoesAnyoneKnowHowToRemoveIt(): Mono<Any>? = null
    }
}
