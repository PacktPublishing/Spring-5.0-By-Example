package springfive.twittergathering.infra.rabbitmq

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * @author claudioed on 20/12/17.
 */
@Configuration
open class RabbitMQConfiguration(@Value("\${queue.twitter}") private val queue:String,
                                 @Value("\${exchange.twitter}") private val exchange:String,
                                 @Value("\${routing_key.track}") private val routingKey:String){

    @Bean
    open fun queue():Queue{
        return Queue(this.queue,false)
    }

    @Bean
    open fun exchange():TopicExchange{
        return TopicExchange(this.exchange)
    }

    @Bean
    open fun binding(queue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(queue).to(exchange).with(this.routingKey)
    }

    @Bean
    open fun converter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter(ObjectMapper().registerModule(KotlinModule()))
    }

}