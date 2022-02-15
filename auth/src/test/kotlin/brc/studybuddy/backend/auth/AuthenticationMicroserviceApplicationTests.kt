package brc.studybuddy.backend.auth

import brc.studybuddy.backend.auth.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AuthenticationMicroserviceApplicationTests {

    lateinit var repository: UserRepository

    @BeforeEach
    internal fun setUp() {
    }

    @Test
    fun contextLoads() {
    }

}
