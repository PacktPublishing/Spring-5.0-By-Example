package springfive.airline.airlinefare.domain.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlinefare.domain.Flight;
import springfive.airline.airlinefare.infra.oauth.Credentials;

@Service
public class FlightService {

  private final DiscoveryService discoveryService;

  private final String flightService;

  private final TokenService tokenService;

  private final Credentials credentials;

  private final WebClient webClient;

  public FlightService(DiscoveryService discoveryService,
      @Value("${flight.service}") String flightService,
      TokenService tokenService,
      @Qualifier("flightsCredentials") Credentials credentials,
      WebClient webClient) {
    this.discoveryService = discoveryService;
    this.flightService = flightService;
    this.tokenService = tokenService;
    this.credentials = credentials;
    this.webClient = webClient;
  }

  public Mono<Flight> flight(String id) {
    return discoveryService.serviceAddressFor(this.flightService).next()
        .flatMap(address -> Mono.just(this.webClient.mutate().baseUrl(address + "/" + id).build().get()))
        .flatMap(requestHeadersUriSpec ->
            Flux.combineLatest(Flux.just(requestHeadersUriSpec),Flux.from(tokenService.token(this.credentials)),(reqSpec, token) ->{
              reqSpec.header("Authorization","Bearer" + token.getToken());
              return reqSpec;
            })
                .next())
        .map(RequestHeadersSpec::retrieve)
        .flatMap(eq -> eq.bodyToMono(Flight.class));
  }

}
