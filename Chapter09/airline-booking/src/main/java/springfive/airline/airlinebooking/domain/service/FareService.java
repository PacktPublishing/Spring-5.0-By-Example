package springfive.airline.airlinebooking.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import springfive.airline.airlinebooking.domain.fare.Fare;

@Service
public class FareService {

  private final DiscoveryService discoveryService;

  private final String fareService;

  private final WebClient webClient;

  public FareService(DiscoveryService discoveryService,
      @Value("${fares.service}") String fareService,
      WebClient webClient) {
    this.discoveryService = discoveryService;
    this.fareService = fareService;
    this.webClient = webClient;
  }

  public Mono<Fare> fare(String id){
    return discoveryService.serviceAddressFor(this.fareService).next().flatMap(
        address -> this.webClient.mutate().baseUrl(address + "/" + id).build().get().exchange()
            .flatMap(clientResponse -> clientResponse.bodyToMono(Fare.class)));
  }

}
