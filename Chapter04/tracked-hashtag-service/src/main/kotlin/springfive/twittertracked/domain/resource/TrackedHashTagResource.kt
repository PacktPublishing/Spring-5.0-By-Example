package springfive.twittertracked.domain.resource

import org.springframework.web.bind.annotation.*
import springfive.twittertracked.domain.TrackedHashTag
import springfive.twittertracked.domain.service.TrackedHashTagService

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