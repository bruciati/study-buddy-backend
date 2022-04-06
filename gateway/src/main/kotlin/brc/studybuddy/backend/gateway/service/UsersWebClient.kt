package brc.studybuddy.backend.gateway.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class UsersWebClient {
    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder
    private val webClient by lazy { webClientBuilder.baseUrl("lb://users").build() }



}
