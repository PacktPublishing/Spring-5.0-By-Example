package springfive.airline.airlinebooking.domain.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlinebooking.domain.Flight;
import springfive.airline.airlinebooking.infra.oauth.Credentials;

@Service
public class FlightService {

  private final DiscoveryService discoveryService;

  private final String flightService;

  private final TokenService tokenService;

  private final Credentials credentials;

  private final WebClient webClient;

  public FlightService(DiscoveryService discoveryService,
      @Value("${flights.service}") String flightService,
      TokenService tokenService,
      @Qualifier("flightsCredentials") Credentials credentials,
      WebClient webClient) {
    this.discoveryService = discoveryService;
    this.flightService = flightService;
    this.tokenService = tokenService;
    this.credentials = credentials;
    this.webClient = webClient;
  }

  @HystrixCommand(commandKey = "flight-by-id",groupKey = "airline-fare",commandProperties = {
      @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
      @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "800"),
      @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
  })
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
        .doOnError(e -> {throw new RuntimeException("Invalid flight");})
        .flatMap(eq -> eq.bodyToMono(Flight.class));
  }

}
