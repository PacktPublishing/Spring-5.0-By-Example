package springfive.airline.airlineplanes.domain.resource;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlineplanes.domain.Plane;
import springfive.airline.airlineplanes.domain.resource.data.PlaneRequest;
import springfive.airline.airlineplanes.domain.service.PlaneService;

@RestController
@RequestMapping("/")
public class PlaneResource {

  private final PlaneService planeService;

  public PlaneResource(PlaneService planeService) {
    this.planeService = planeService;
  }

  @GetMapping
  public Flux<Plane> planes() {
    return this.planeService.planes();
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Plane>> plane(@PathVariable("id") String id) {
    return this.planeService.plane(id).map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<Void>> newPlane(
      @Valid @RequestBody PlaneRequest planeRequest, UriComponentsBuilder uriBuilder) {
    return this.planeService.create(planeRequest).map(data -> {
      URI location = uriBuilder.path("/planes/{id}")
          .buildAndExpand(data.getId())
          .toUri();
      return ResponseEntity.created(location).build();
    });
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Object>> deletePlane(@PathVariable("id") String id) {
    return this.planeService.plane(id).flatMap(data -> this.planeService.deletePlane(data)
        .then(Mono.just(ResponseEntity.noContent().build())))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Object>> updatePlane(@PathVariable("id") String id,@Valid @RequestBody PlaneRequest planeRequest) {
    return this.planeService.update(id,planeRequest)
        .then(Mono.just(ResponseEntity.ok().build()));
  }

}
