package springfive.airline.airlinefare.domain.resource;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import springfive.airline.airlinefare.domain.Fare;
import springfive.airline.airlinefare.domain.FareQuery;
import springfive.airline.airlinefare.domain.service.FareService;

@RestController
@RequestMapping("/fares")
public class FareResource {

  private final FareService fareService;

  public FareResource(FareService fareService) {
    this.fareService = fareService;
  }

  @PostMapping
  public Mono<ResponseEntity<Fare>> requestFare(@RequestBody FareQuery fareQuery,UriComponentsBuilder uriBuilder){
    return this.fareService.requestFare(fareQuery).map(data -> {
      URI location = uriBuilder.path("/fares/{id}")
          .buildAndExpand(data.getId())
          .toUri();
      return ResponseEntity.created(location).build();
    });
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Fare>> fare(@PathVariable("id") String id){
    return this.fareService.fare(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
  }

}
