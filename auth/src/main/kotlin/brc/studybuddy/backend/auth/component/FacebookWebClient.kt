package brc.studybuddy.backend.auth.component

import brc.studybuddy.backend.auth.model.FacebookResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class FacebookWebClient(
    @Value("\${secrets.facebook.apptoken}") private val appAccessToken: String
) {

    @Autowired
    lateinit var webClientBuilder: WebClient.Builder
    val webClient by lazy { webClientBuilder.baseUrl("https://graph.facebook.com/").build() }

    fun getTokenInfo(token: String): Mono<FacebookResponse> {
        return webClient.get()
            .uri("/debug_token?input_token=$token&access_token=$appAccessToken")
            .retrieve()
            .bodyToMono(FacebookResponse::class.java)
    }

}