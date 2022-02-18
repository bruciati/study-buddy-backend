package brc.studybuddy.backend.users.config

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Delivery
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.amqp.RabbitProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.rabbitmq.*
import javax.annotation.PostConstruct

const val RMQ_USERS_QUEUE = "microservice.users.queue"
const val RMQ_GROUPS_QUEUE = "microservice.groups.queue"

@Configuration
class RabbitMqConfiguration {
    @Autowired
    private lateinit var amqpAdmin: AmqpAdmin

    @Bean
    fun rabbitConnection(rabbitProperties: RabbitProperties): Mono<Connection> =
        Mono.fromCallable {
            val connectionFactory = ConnectionFactory()
            connectionFactory.host = rabbitProperties.host
            connectionFactory.port = rabbitProperties.port
            connectionFactory.username = rabbitProperties.username
            connectionFactory.password = rabbitProperties.password
            connectionFactory.useNio()

            connectionFactory.newConnection()
        }.cache()

    @Bean
    fun sender(rabbitConnection: Mono<Connection>): Sender =
        RabbitFlux.createSender(SenderOptions().connectionMono(rabbitConnection))

    @Bean
    fun receiver(rabbitConnection: Mono<Connection>): Receiver =
        RabbitFlux.createReceiver(ReceiverOptions().connectionMono(rabbitConnection))

    @Bean
    fun deliveryFlux(receiver: Receiver): Flux<Delivery> = receiver.consumeNoAck(RMQ_USERS_QUEUE)

    @PostConstruct
    fun init() {
        amqpAdmin.declareQueue(Queue(RMQ_USERS_QUEUE, false, false, true))
    }
}
