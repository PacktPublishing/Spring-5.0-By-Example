package springfive.airline.airlinebooking.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlinebooking.domain.fare.Fare;

@Service
public class FlightService {

  private final DiscoveryService discoveryService;

  private final String fareService;

  private final WebClient webClient;

  private final TokenService tokenService;

  public FlightService(DiscoveryService discoveryService,
      @Value("${fares.service}") String fareService,
      WebClient webClient,
      TokenService tokenService) {
    this.discoveryService = discoveryService;
    this.fareService = fareService;
    this.webClient = webClient;
    this.tokenService = tokenService;
  }

  public Mono<Fare> fare(String id){
    return discoveryService.serviceAddressFor(this.fareService).next()
        .flatMap(address -> Mono.just(this.webClient.mutate().baseUrl(address + "/" + id).build().get()))
        .flatMap(requestHeadersUriSpec ->
            Flux.combineLatest(Flux.just(requestHeadersUriSpec),Flux.from(tokenService.token()),(reqSpec, token) ->{
              reqSpec.header("Authorization","Bearer" + token.getToken());
              return reqSpec;
            })
                .next())
        .map(RequestHeadersSpec::retrieve)
        .flatMap(eq -> eq.bodyToMono(Fare.class));
  }

}
