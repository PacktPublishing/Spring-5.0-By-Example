package springfive.twitterconsumer.domain.service

import org.springframework.stereotype.Service
import springfive.twitterconsumer.domain.TrackedHashTag
import springfive.twitterconsumer.domain.repository.TrackedHashTagRepository

/**
 * @author claudioed on 06/12/17.
 */
@Service
class TrackedHashTagService(private val repository: TrackedHashTagRepository) {

    fun save(hashTag:TrackedHashTag) = this.repository.save(hashTag)

    fun all() = this.repository.findAll()

}