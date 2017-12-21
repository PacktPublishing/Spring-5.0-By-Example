package springfive.twitterconsumer.domain.resource

import org.springframework.web.bind.annotation.*
import springfive.twitterconsumer.domain.TrackedHashTag
import springfive.twitterconsumer.domain.service.TrackedHashTagService

/**
 * @author claudioed on 06/12/17.
 */
@RestController
@RequestMapping("/api/tracked-hash-tag")
class TrackedHashTagResource(private val service:TrackedHashTagService) {

    @GetMapping
    fun all() = this.service.all()

    @PostMapping
    fun save(@RequestBody hashTag:TrackedHashTag) = this.service.save(hashTag)

}