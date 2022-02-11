package brc.studybuddy.backend.template.wrapper

import brc.studybuddy.backend.template.wrapper.model.Response
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.web.WebProperties.Resources
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.result.view.ViewResolver
import reactor.core.publisher.Mono
import java.util.stream.Collectors

@Component
@Order(-2)
class WebExceptionWrapper(
    viewResolvers: ObjectProvider<ViewResolver>, serverCodecConfigurer: ServerCodecConfigurer,
    errorAttributes: ErrorAttributes, resources: Resources, applicationContext: ApplicationContext
) : AbstractErrorWebExceptionHandler(errorAttributes, resources, applicationContext) {
    init {
        super.setMessageWriters(serverCodecConfigurer.writers)
        super.setMessageReaders(serverCodecConfigurer.readers)
        super.setViewResolvers(viewResolvers.orderedStream().collect(Collectors.toList()))
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> =
        RouterFunctions.route(RequestPredicates.all()) { req -> renderErrorResponse(req) }

    private fun renderErrorResponse(serverRequest: ServerRequest): Mono<ServerResponse> {
        val errorPropertiesMap: Map<String, Any> = getErrorAttributes(
            serverRequest,
            ErrorAttributeOptions.defaults()
        )

        val responseError = Response.Error(errorPropertiesMap["status"] as Int, errorPropertiesMap["error"] as String?)
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(Response(false, null, responseError)))
    }
}
