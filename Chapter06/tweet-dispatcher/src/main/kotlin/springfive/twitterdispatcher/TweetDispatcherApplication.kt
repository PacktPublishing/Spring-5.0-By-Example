package springfive.twitterdispatcher

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * @author claudioed on 02/12/17.
 */
@SpringBootApplication
class TweetDispatcherApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(TweetDispatcherApplication::class.java, *args)
        }
    }

}

