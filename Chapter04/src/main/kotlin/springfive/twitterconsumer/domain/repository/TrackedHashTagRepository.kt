package springfive.domain.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import springfive.domain.TrackedHashTag

/**
 * @author claudioed on 06/12/17.
 * Project twitter-consumer
 */
interface TrackedHashTagRepository:ReactiveCrudRepository<TrackedHashTag,String>