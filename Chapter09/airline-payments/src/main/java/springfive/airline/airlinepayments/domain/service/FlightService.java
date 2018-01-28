package springfive.airline.airlinepayments.domain.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FlightService {

  private final DiscoveryService discoveryService;

  public FlightService(DiscoveryService discoveryService) {
    this.discoveryService = discoveryService;
  }

  public Mono<FlightData> flightData(String flightId){
    return Mono.empty();
  }

}
