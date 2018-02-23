package springfive.airline.airlineecommerce.domain.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.lang.reflect.Field;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlineecommerce.domain.FlightSearch;
import springfive.airline.airlineecommerce.domain.flight.Flight;
import springfive.airline.airlineecommerce.domain.service.data.AvailableSeats;
import springfive.airline.airlineecommerce.infra.oauth.Credentials;

@Service
public class FlightService {

  private final WebClient webClient;

  private final String flightsService;

  private final DiscoveryService discoveryService;

  private final TokenService tokenService;

  private final Credentials flightsCredentials;

  public FlightService(WebClient webClient, DiscoveryService discoveryService,TokenService tokenService,
      @Value("${flights.service}") String flightsService,@Qualifier("flightsCredentials") Credentials flightsCredentials) {
    this.webClient = webClient;
    this.discoveryService = discoveryService;
    this.flightsService = flightsService;
    this.tokenService = tokenService;
    this.flightsCredentials = flightsCredentials;
  }

  @HystrixCommand(commandKey = "flight-query",groupKey = "airline-flights-query",commandProperties = {
      @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
      @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "800"),
      @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
  })
  public Flux<Flight> search(@NonNull FlightSearch query){
    return discoveryService.serviceAddressFor(this.flightsService).next()
        .flatMap(address -> Mono
            .just(this.webClient.mutate().baseUrl(address + "/query").build().post().body(
                BodyInserters.fromObject(query))))
        .flatMap(requestHeadersUriSpec ->
            Flux.combineLatest(Flux.just(requestHeadersUriSpec),Flux.from(tokenService.token(this.flightsCredentials)),(reqSpec, token) ->{
              reqSpec.header("Authorization","Bearer" + token.getToken());
              return reqSpec;
            })
                .next())
        .map(RequestHeadersSpec::retrieve)
        .flatMapMany(res -> res.bodyToFlux(Flight.class));
  }

  @HystrixCommand(commandKey = "available-seats-query",groupKey = "airline-flights-query",commandProperties = {
      @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
      @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "800"),
      @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
  })
  public Mono<AvailableSeats> availableSeats(@NonNull String flightId){
    return discoveryService.serviceAddressFor(this.flightsService).next()
        .flatMap(address -> Mono
            .just(this.webClient.mutate().baseUrl(address +"/" + flightId+ "/available").build().get()))
        .flatMap(requestHeadersUriSpec ->
            Flux.combineLatest(Flux.just(requestHeadersUriSpec),Flux.from(tokenService.token(this.flightsCredentials)),(reqSpec, token) ->{
              reqSpec.header("Authorization","Bearer" + token.getToken());
              return reqSpec;
            })
                .next())
        .map(RequestHeadersSpec::retrieve)
        .flatMap(res -> res.bodyToMono(AvailableSeats.class));
  }

}
