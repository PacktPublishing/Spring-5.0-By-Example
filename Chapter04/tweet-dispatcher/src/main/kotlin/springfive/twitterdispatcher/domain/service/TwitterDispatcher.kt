package springfive.twitterdispatcher.domain.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.rabbitmq.Receiver

/**
 * @author claudioed on 26/12/17.
 */
@Service
class TwitterDispatcher(private @Value("\${queue.twitter}") val queue: String,
                        private val receiver: Receiver,
                        private val mapper: ObjectMapper) {

    fun dispatch(): Flux<Tweet> {
        return this.receiver.consumeAutoAck(this.queue).flatMap { message ->
            Mono.just(mapper.readValue<Tweet>(String(message.body)))
        }
    }

}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Tweet(val id: String = "", val text: String = "", @JsonProperty("created_at") val createdAt: String = "", val user: TwitterUser = TwitterUser("", ""))

@JsonIgnoreProperties(ignoreUnknown = true)
data class TwitterUser(val id: String, val name: String)