package brc.studybuddy.backend.meetings.service

import brc.studybuddy.backend.meetings.repository.MeetingsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient


@Service
class UserClientService {

    @Autowired
    private lateinit var webClientBuilder: WebClient.Builder
    private val webClient: WebClient by lazy(webClientBuilder.baseUrl("lb://users")::build)




}