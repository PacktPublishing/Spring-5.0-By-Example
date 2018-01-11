package springfive.twittergathering

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * @author claudioed on 02/12/17.
 * Project twitter-consumer
 */
@SpringBootApplication
@EnableScheduling
open class TweetGatheringApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println(System.getenv().toString())
            SpringApplication.run(TweetGatheringApplication::class.java, *args)
        }
    }

}

