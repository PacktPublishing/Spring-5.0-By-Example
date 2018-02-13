package springfive.airline.airlinefare.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import springfive.airline.airlinefare.domain.Flight;

@Service
public class FlightService {

  private final DiscoveryService discoveryService;

  private final String flightService;

  private final String flightServiceApiPath;

  private final WebClient webClient;

  public FlightService(DiscoveryService discoveryService,
      @Value("${flight.service}") String flightService,
      @Value("${flight.path}") String flightServiceApiPath,
      WebClient webClient) {
    this.discoveryService = discoveryService;
    this.flightService = flightService;
    this.flightServiceApiPath = flightServiceApiPath;
    this.webClient = webClient;
  }

  public Mono<Flight> flight(String id) {
    return discoveryService.serviceAddressFor(this.flightService).next().flatMap(
        address -> this.webClient.mutate()
            .baseUrl(address + "/" + this.flightServiceApiPath + "/" + id).build().get().exchange()
            .flatMap(clientResponse -> clientResponse.bodyToMono(Flight.class)));
  }

}
