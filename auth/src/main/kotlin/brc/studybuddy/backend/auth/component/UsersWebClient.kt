package brc.studybuddy.backend.auth.component

import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

internal const val USERS_BASEURL = "lb://users"
internal const val USERS_ENDPOINT = "/users"

@Component
class UsersWebClient {

    @Autowired
    lateinit var webClientBuilder: WebClient.Builder
    val webClient by lazy { webClientBuilder.baseUrl("lb://users").build() }

    fun getUserByEmail(email: String): Mono<User> =
            webClient.get()
                .uri("/users/email/{email}", email)
                .retrieve()
                .bodyToMono(User::class.java)

}