package springfive.twitterconsumer.domain.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import springfive.twitterconsumer.infra.twitter.Twitter
import springfive.twitterconsumer.infra.twitter.TwitterAppSettings
import springfive.twitterconsumer.infra.twitter.TwitterToken


/**
 * @author claudioed on 10/12/17.
 */
@Service
class TweetGatherService(private val twitterAppSettings: TwitterAppSettings,
                         private val twitterToken: TwitterToken,
                         private val webClient: WebClient) {

    private val authUrl = "https://api.twitter.com/oauth2/token?grant_type=client_credentials"

//    fun tweetsFrom(query: String): Mono<Statuses>? {
//        return login().flatMap { token ->
//            return@flatMap this.webClient.mutate().baseUrl("https://api.twitter.com/1.1/search/tweets.json?q=$query").build()
//                    .get()
//                    .header("Authorization", "Bearer " + token.accessToken)
//                    .retrieve().bodyToMono(Statuses::class.java)
//        }
//    }

//    fun realTimeTweets(query: String): Flux<String>? {
//        return this.webClient.mutate().baseUrl("https://stream.twitter.com/1.1/statuses/filter.json?track=$query").build()
//                .get()
//                .header("Authorization", "Bearer " + EncodeUtils.generateHeader("gupfxwn43NBTdxCD3Tsf1JgMu", "pH4uM5LlYxKzfJ7huYRwFbaFXn7ooK01LmqCP69QV9a9kZrHw5"))
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve().bodyToFlux(String::class.java)
//                .doOnError({ t: Throwable? -> print(t?.message) })
//    }


    fun streamFrom(query:String): Flux<Tweet> {
        val url = "https://stream.twitter.com/1.1/statuses/filter.json"
        return this.webClient.mutate().baseUrl(url).build()
                .post()
                .body(BodyInserters.fromFormData("track",query))
                .header("Authorization", Twitter.buildAuthHeader(twitterAppSettings,twitterToken,"POST",url,query))
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve().bodyToFlux(Tweet::class.java)
    }



}

data class TwitterToken(@JsonProperty("token_type") val tokenType: String, @JsonProperty("access_token") val accessToken: String)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Statuses(val statuses: List<Tweet>)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Tweet(val id: String = "", val text: String = "", @JsonProperty("created_at") val createdAt: String = "", val user: TwitterUser = TwitterUser("",""))

@JsonIgnoreProperties(ignoreUnknown = true)
data class TwitterUser(val id: String, val name: String)
