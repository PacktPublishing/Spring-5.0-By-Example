package springfive.airline.airlinefare.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.util.Set;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlinefare.domain.Booking;
import springfive.airline.airlinefare.infra.oauth.Credentials;

@Service
public class BookingService {

  private final DiscoveryService discoveryService;

  private final String bookingService;

  private final WebClient webClient;

  private final TokenService tokenService;

  private final Credentials credentials;

  public BookingService(DiscoveryService discoveryService,
      @Value("${bookings.service}") String bookingService,
      WebClient webClient,
      TokenService tokenService,
      @Qualifier("bookingsCredentials") Credentials credentials) {
    this.discoveryService = discoveryService;
    this.bookingService = bookingService;
    this.webClient = webClient;
    this.tokenService = tokenService;
    this.credentials = credentials;
  }

  @HystrixCommand(commandKey = "bookings-by-flight-id",groupKey = "airline-fare",commandProperties = {
      @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
      @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "800"),
      @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
  })
  public Mono<Set<Booking>> bookingOfFlight(String flightId) {
    return discoveryService.serviceAddressFor(this.bookingService).next()
        .flatMap(address -> Mono.just(this.webClient.mutate().baseUrl(address + "/flight/" + flightId).build().get()))
        .flatMap(requestHeadersUriSpec ->
            Flux.combineLatest(Flux.just(requestHeadersUriSpec),Flux.from(tokenService.token(this.credentials)),(reqSpec, token) ->{
              reqSpec.header("Authorization","Bearer" + token.getToken());
              return reqSpec;
            })
                .next())
        .map(RequestHeadersSpec::retrieve)
        .flatMap(eq -> {
          ParameterizedTypeReference<Set<Booking>> typeRef = new ParameterizedTypeReference<Set<Booking>>() {};
          return eq.bodyToMono(typeRef);
        });
  }

}
