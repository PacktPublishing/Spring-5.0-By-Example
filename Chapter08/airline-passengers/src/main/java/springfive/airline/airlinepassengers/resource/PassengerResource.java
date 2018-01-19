package springfive.airline.airlinepassengers.resource;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlinepassengers.domain.Passenger;
import springfive.airline.airlinepassengers.resource.data.PassengerRequest;
import springfive.airline.airlinepassengers.service.PassengerService;

@RestController
@RequestMapping("/passengers")
public class PassengerResource {

  private final PassengerService passengerService;

  public PassengerResource(PassengerService passengerService) {
    this.passengerService = passengerService;
  }

  @GetMapping
  public Flux<Passenger> passengers(){
    return this.passengerService.passengers();
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Passenger>> passengers(@PathVariable("id") String id){
    return this.passengerService.passenger(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<?>> passengers(@Valid @RequestBody PassengerRequest passengerRequest,UriComponentsBuilder uriBuilder ){
    return this.passengerService.create(passengerRequest).map(data ->{
      URI location =  uriBuilder.path("/passengers/{id}")
          .buildAndExpand(data.getId())
          .toUri();
      return ResponseEntity.created(location).build();
    });
  }

}
