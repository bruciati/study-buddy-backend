package brc.studybuddy.backend.auth.controllers

import brc.studybuddy.backend.auth.models.ServiceError
import brc.studybuddy.backend.auth.models.ServiceResponse
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.WebProperties
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
class GlobalErrorHandler(
    viewResolvers: ObjectProvider<ViewResolver>, serverCodecConfigurer: ServerCodecConfigurer,
    errorAttributes: ErrorAttributes, resources: WebProperties.Resources, applicationContext: ApplicationContext
) : AbstractErrorWebExceptionHandler(errorAttributes, resources, applicationContext) {

    init {
        super.setMessageWriters(serverCodecConfigurer.writers)
        super.setMessageReaders(serverCodecConfigurer.readers)
        super.setViewResolvers(viewResolvers.orderedStream().collect(Collectors.toList()))
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes): RouterFunction<ServerResponse> {
        return RouterFunctions.route(RequestPredicates.all()) {
            serverRequest: ServerRequest -> renderErrorResponse(serverRequest)
        }
    }

    private fun renderErrorResponse(serverRequest: ServerRequest): Mono<ServerResponse?> {
        val errorPropertiesMap: Map<String, Any> = getErrorAttributes(
            serverRequest,
            ErrorAttributeOptions.defaults()
        )
        val errorResponse = ServiceError(errorPropertiesMap["status"] as Int, errorPropertiesMap["error"] as String?)
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(ServiceResponse(false, null, errorResponse)))
    }
}