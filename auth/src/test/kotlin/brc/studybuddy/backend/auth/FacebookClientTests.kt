package brc.studybuddy.backend.auth

import brc.studybuddy.backend.auth.component.FacebookWebClient
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@TestPropertySource(properties = ["secrets.facebook.apptoken=475499464360666|14f84178a249eb0aa7a454fc7535b040"])
@SpringBootTest
class FacebookClientTests {

    @Autowired
    lateinit var facebookClient: FacebookWebClient

    @Test
    fun testFacebookTokenInfo() {
        val token = "TOKEN_TO_TEST"
        facebookClient.getTokenInfo(token)
            .subscribe {
                println(it.toString())
            }
    }

}