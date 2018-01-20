package springfive.airline.airlineflights.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import springfive.airline.airlineflights.domain.Flight;
import springfive.airline.airlineflights.resource.data.FlightRequest;
import springfive.airline.airlineflights.service.FlightService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/flights")
public class FlightResource {

  private final FlightService flightService;

  public FlightResource(FlightService flightService) {
    this.flightService = flightService;
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Flight>> flight(@PathVariable("id")String id){
    return this.flightService.flight(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<Void>> newFlight(@Valid @RequestBody FlightRequest flightRequest, UriComponentsBuilder uriBuilder){
    return this.flightService.save(flightRequest).map(data -> {
      URI location = uriBuilder.path("/flights/{id}")
              .buildAndExpand(data.getId())
              .toUri();
      return ResponseEntity.created(location).build();
    });
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Object>> deleteFlight(@PathVariable("id") String id) {
    return this.flightService.flight(id).flatMap(data -> this.flightService.deleteFlight(data)
            .then(Mono.just(ResponseEntity.noContent().build())))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Object>> updateFlight(@PathVariable("id") String id,@Valid @RequestBody FlightRequest flightRequest) {
    return this.flightService.flight(id).flatMap(data -> this.flightService.update(id,flightRequest)
            .then(Mono.just(ResponseEntity.ok().build())))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}
