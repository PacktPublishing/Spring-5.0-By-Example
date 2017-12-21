package springfive.twitterconsumer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * @author claudioed on 02/12/17.
 * Project twitter-consumer
 */
@SpringBootApplication
@EnableScheduling
open class TrackedHashTagApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(TrackedHashTagApplication::class.java, *args)
        }
    }

}

