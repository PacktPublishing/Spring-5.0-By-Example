package springfive.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

/**
 * @author claudioed on 06/12/17.
 * Project twitter-consumer
 */
@RedisHash
data class TrackedHashTag(@Id val hashTag: String )