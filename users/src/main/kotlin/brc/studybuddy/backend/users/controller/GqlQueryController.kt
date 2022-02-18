package brc.studybuddy.backend.users.controller

import brc.studybuddy.backend.users.config.RMQ_GROUPS_QUEUE
import brc.studybuddy.backend.users.repository.UsersRepository
import brc.studybuddy.database.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.rabbitmq.RpcClient
import reactor.rabbitmq.Sender
import javax.annotation.PreDestroy

@Controller
class GqlQueryController {
    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var rabbitSender: Sender

    // Thread-Safe on-demand/lazy value initializer
    private val groupsRpcClient: RpcClient by lazy { rabbitSender.rpcClient("", RMQ_GROUPS_QUEUE) }

    @QueryMapping
    fun users(): Flux<User> =
        usersRepository.findAll()
            .flatMap { u ->
                groupsRpcClient.rpc(Mono.just(RpcClient.RpcRequest("users#${u.id}".toByteArray()))).map {
                    println(String(it.body))
                    u
                }
            }

    @QueryMapping
    fun userById(@Argument id: Long): Mono<User> {
        return groupsRpcClient.rpc(Mono.just(RpcClient.RpcRequest("userById#$id".toByteArray())))
            .flatMap {
                println(String(it.body))
                usersRepository.findById(id)
            }
    }

    @QueryMapping
    fun userByEmail(@Argument email: String): Mono<User> =
        groupsRpcClient.rpc(Mono.just(RpcClient.RpcRequest("userByEmail#$email".toByteArray())))
            .flatMap {
                println(String(it.body))
                usersRepository.findByEmail(email)
            }

    @PreDestroy
    fun closeRpcClient() {
        groupsRpcClient.close()
    }
}
