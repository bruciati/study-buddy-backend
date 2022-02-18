package brc.studybuddy.backend.users

import com.rabbitmq.client.Connection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.core.publisher.Mono
import javax.annotation.PreDestroy

@SpringBootApplication
class UsersMicroserviceApplication {
    @Autowired
    private lateinit var rabbitConnection: Mono<Connection>

    @PreDestroy
    @Throws(Exception::class)
    fun close() {
        rabbitConnection.block()!!.close()
    }
}

fun main(args: Array<String>) {
    runApplication<UsersMicroserviceApplication>(*args)
}
