package springfive.twitterconsumer.domain.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import springfive.twitterconsumer.domain.service.Tweet
import springfive.twitterconsumer.domain.service.TwitterDispatcher

/**
 * @author claudioed on 26/12/17.
 * Project tweet-dispatcher
 */
@RestController
@RequestMapping("/tweets")
class TweetResource(private val dispatcher: TwitterDispatcher) {

    @GetMapping(produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun tweets(@RequestParam("q")query:String):Flux<Tweet>{
        return dispatcher.dispatch().filter({ tweet: Tweet? -> tweet!!.text.contains(query,ignoreCase = true) })
    }

}