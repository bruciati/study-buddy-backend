package brc.studybuddy.backend.auth.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import java.time.Duration


@Component
class FacebookWebClient {

    @Autowired
    lateinit var webClientBuilder: WebClient.Builder
    val webClient by lazy { webClientBuilder.baseUrl("https://graph.facebook.com/").build() }

    fun getTokenInfo(token: String): Mono<String> {
        return webClient.get()
            .uri("/debug_token?input_token=$token&access_token=APP_ACCESS_TOKEN")
            .retrieve()
            .bodyToMono(String::class.java)
    }

}