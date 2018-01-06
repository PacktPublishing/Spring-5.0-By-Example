package springfive.domain.resource

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfive.domain.TrackedHashTag
import springfive.domain.service.TrackedHashTagService

/**
 * @author claudioed on 06/12/17.
 * Project twitter-consumer
 */
@RestController
@RequestMapping("/api/tracked-hash-tag")
class TrackedHashTagResource(private val service:TrackedHashTagService) {

    @GetMapping
    fun all() = this.service.all()

    fun save(@RequestBody hashTag:TrackedHashTag) = this.service.save(hashTag)


}