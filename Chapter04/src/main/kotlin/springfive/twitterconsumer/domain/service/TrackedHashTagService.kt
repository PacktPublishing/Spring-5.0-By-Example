package springfive.domain.service

import org.springframework.stereotype.Service
import springfive.domain.TrackedHashTag
import springfive.domain.repository.TrackedHashTagRepository

/**
 * @author claudioed on 06/12/17.
 * Project twitter-consumer
 */
@Service
class TrackedHashTagService(private val repository: TrackedHashTagRepository) {

    fun save(hashTag:TrackedHashTag) = this.repository.save(hashTag)

    fun all() = this.repository.findAll()

}