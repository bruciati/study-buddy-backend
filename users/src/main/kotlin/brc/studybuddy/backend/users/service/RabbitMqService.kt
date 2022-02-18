package brc.studybuddy.backend.users.service

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Delivery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.rabbitmq.OutboundMessage
import reactor.rabbitmq.Sender

@Service
class RabbitMqService : CommandLineRunner {
    @Autowired
    private lateinit var deliveryFlux: Flux<Delivery>

    @Autowired
    private lateinit var sender: Sender

    override fun run(vararg args: String?) {
        deliveryFlux
            .flatMap { d -> sendResponse(d.properties.replyTo, d.properties.correlationId, String(d.body)) }
            .subscribe()
    }

    // Echo response
    private fun <T> sendResponse(replyTo: String, correlationId: String, value: T): Mono<Void> =
        sender.send(
            Mono.fromCallable { // TODO Proper serialization
                val basicProperties = AMQP.BasicProperties.Builder().correlationId(correlationId).build()
                OutboundMessage("", replyTo, basicProperties, (value as String).toByteArray())
            }
        )
}
