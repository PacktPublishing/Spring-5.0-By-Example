package springfive.twitterdispatcher.infra.rabbitmq

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.rabbitmq.client.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.rabbitmq.ReactorRabbitMq
import reactor.rabbitmq.Receiver
import reactor.rabbitmq.ReceiverOptions


/**
 * @author claudioed on 20/12/17.
 */
@Configuration
class RabbitMQConfiguration(private @Value("\${spring.rabbitmq.host}") val host: String,
                            private @Value("\${spring.rabbitmq.port}") val port: Int,
                            private @Value("\${spring.rabbitmq.username}") val username: String,
                            private @Value("\${spring.rabbitmq.password}") val password: String) {

    @Bean
    fun mapper(): ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    @Bean
    fun connectionFactory(): ConnectionFactory {
        val connectionFactory = ConnectionFactory()
        connectionFactory.username = this.username
        connectionFactory.password = this.password
        connectionFactory.host = this.host
        connectionFactory.port = this.port
        connectionFactory.useNio()
        return connectionFactory
    }

    @Bean
    fun receiver(connectionFactory: ConnectionFactory): Receiver {
        val options = ReceiverOptions()
        options.connectionFactory(connectionFactory)
        return ReactorRabbitMq.createReceiver(options)
    }

}