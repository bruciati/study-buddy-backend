package brc.studybuddy.backend.groups

import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@EnableEurekaClient
@SpringBootApplication
class GroupsMicroserviceApplication {

    @Bean
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    fun dbConnInitializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer =
        with(ConnectionFactoryInitializer()) {
            this.setConnectionFactory(connectionFactory)
            this.setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("schema.sql")))
            return this
        }

}

fun main(args: Array<String>) {
    runApplication<GroupsMicroserviceApplication>(*args)
}