package springfive.airline.airlineflights.domain.resource;

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
import springfive.airline.airlineflights.domain.Flight;
import springfive.airline.airlineflights.domain.resource.data.FlightQuery;
import springfive.airline.airlineflights.domain.resource.data.FlightRequest;
import springfive.airline.airlineflights.domain.service.FlightService;

@RestController
@RequestMapping("/")
public class FlightResource {

  private final FlightService flightService;

  public FlightResource(FlightService flightService) {
    this.flightService = flightService;
  }

  @GetMapping
  public Flux<Flight> flights(){
    return this.flightService.flights();
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

  @PostMapping("/query")
  public Flux<Flight> flights(@Valid @RequestBody FlightQuery query){
    return this.flightService.flightBy(query);
  }

}