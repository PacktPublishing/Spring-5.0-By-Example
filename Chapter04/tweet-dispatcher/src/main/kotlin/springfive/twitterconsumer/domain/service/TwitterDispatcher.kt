package springfive.twitterconsumer.domain.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

/**
 * @author claudioed on 10/12/17.
 * Project twitter-consumer
 */
@Service
class TwitterDispatcher {

    @RabbitListener(queues = ["twitter-stream"])
    fun receive(tweet:Tweet) {
        println(tweet)
    }

}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Tweet(val id: String = "", val text: String = "", @JsonProperty("created_at") val createdAt: String = "", val user: TwitterUser = TwitterUser("",""))

@JsonIgnoreProperties(ignoreUnknown = true)
data class TwitterUser(val id: String, val name: String)