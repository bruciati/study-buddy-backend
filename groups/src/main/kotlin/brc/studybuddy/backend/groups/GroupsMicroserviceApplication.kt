package brc.studybuddy.backend.groups

import com.rabbitmq.client.Connection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.core.publisher.Mono
import javax.annotation.PreDestroy

@SpringBootApplication
class GroupsMicroserviceApplication {
    @Autowired
    private lateinit var rabbitConnection: Mono<Connection>

    @PreDestroy
    @Throws(Exception::class)
    fun close() {
        rabbitConnection.block()!!.close()
    }
}

fun main(args: Array<String>) {
    runApplication<GroupsMicroserviceApplication>(*args)
}
