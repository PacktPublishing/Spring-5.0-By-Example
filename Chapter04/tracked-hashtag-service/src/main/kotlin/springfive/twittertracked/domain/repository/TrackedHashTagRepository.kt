package springfive.twittertracked.domain.repository

import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import springfive.twittertracked.domain.TrackedHashTag

/**
 * @author claudioed on 06/12/17.
 */
@Service
class TrackedHashTagRepository(private val redisTemplate: ReactiveRedisTemplate<String, String>) {

    fun save(trackedHashTag: TrackedHashTag): Mono<TrackedHashTag> {
        return this.redisTemplate.opsForSet()
                .add("hash-tags", "${trackedHashTag.hashTag}:${trackedHashTag.queue}")
                .flatMap( { data -> Mono.just(trackedHashTag)})
    }

    fun findAll(): Flux<TrackedHashTag> {
        return this.redisTemplate.opsForSet().members("hash-tags").flatMap { el ->
            val data = el.split(":")
            Flux.just(TrackedHashTag(hashTag = data[0],queue = data[1]))
        }
    }

}