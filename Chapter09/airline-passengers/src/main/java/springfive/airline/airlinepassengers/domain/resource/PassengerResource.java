package springfive.airline.airlinepassengers.domain.resource;

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
import springfive.airline.airlinepassengers.domain.Passenger;
import springfive.airline.airlinepassengers.domain.resource.data.PassengerRequest;
import springfive.airline.airlinepassengers.domain.service.PassengerService;

@RestController
@RequestMapping("/passengers")
public class PassengerResource {

  private final PassengerService passengerService;

  public PassengerResource(PassengerService passengerService) {
    this.passengerService = passengerService;
  }

  @GetMapping
  public Flux<Passenger> passengers() {
    return this.passengerService.passengers();
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Passenger>> passengers(@PathVariable("id") String id) {
    return this.passengerService.passenger(id).map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<Void>> newPassenger(
      @Valid @RequestBody PassengerRequest passengerRequest, UriComponentsBuilder uriBuilder) {
    return this.passengerService.create(passengerRequest).map(data -> {
      URI location = uriBuilder.path("/passengers/{id}")
          .buildAndExpand(data.getId())
          .toUri();
      return ResponseEntity.created(location).build();
    });
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Object>> deletePassenger(@PathVariable("id") String id) {
    return this.passengerService.passenger(id).flatMap(data -> this.passengerService.delete(data)
        .then(Mono.just(ResponseEntity.noContent().build())))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Object>> updatePassenger(@PathVariable("id") String id,@Valid @RequestBody PassengerRequest passengerRequest) {
    return this.passengerService.passenger(id).flatMap(data -> this.passengerService.delete(data.fromPassengerRequest(passengerRequest))
        .then(Mono.just(ResponseEntity.ok().build())))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}
