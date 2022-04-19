package brc.studybuddy.backend.auth.component

import brc.studybuddy.input.UserInput
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

internal const val USERS_BASEURL = "lb://users"

@Component
class UsersWebClient {

    @Autowired
    lateinit var webClientBuilder: WebClient.Builder
    val webClient by lazy { webClientBuilder.baseUrl(USERS_BASEURL).build() }

    fun getUserByEmail(email: String): Mono<User> =
            webClient.get()
                .uri("/users/email/{email}", email)
                .retrieve()
                .bodyToMono(User::class.java)

    fun insertFacebookUser(email: String, facebookId: Long): Mono<User> {
        return webClient.post()
                .uri("/users")
                .bodyValue(UserInput(email, User.AuthType.FACEBOOK, facebookId.toString()))
                .retrieve()
                .bodyToMono(User::class.java)
    }

}