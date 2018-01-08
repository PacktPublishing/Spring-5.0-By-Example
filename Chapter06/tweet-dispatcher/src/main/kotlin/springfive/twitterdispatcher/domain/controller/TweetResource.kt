package springfive.twitterdispatcher.domain.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import springfive.twitterdispatcher.domain.service.Tweet
import springfive.twitterdispatcher.domain.service.TwitterDispatcher

/**
 * @author claudioed on 26/12/17.
 */
@RestController
@RequestMapping("/tweets")
class TweetResource(private val dispatcher: TwitterDispatcher) {

    @GetMapping(produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun tweets(@RequestParam("q")query:String):Flux<Tweet>{
        return dispatcher.dispatch().filter({ tweet: Tweet? -> tweet!!.text.contains(query,ignoreCase = true) })
    }

}