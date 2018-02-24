package springfive.airline.airlineflights.domain.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlineflights.domain.service.data.TotalBooked;
import springfive.airline.airlineflights.infra.oauth.Credentials;

@Service
public class BookingService {

  private final WebClient webClient;

  private final String bookingsService;

  private final DiscoveryService discoveryService;

  private final TokenService tokenService;

  private final Credentials credentials;

  public BookingService(WebClient webClient, DiscoveryService discoveryService,
      @Value("${bookings.service}") String bookingsService,
      TokenService tokenService,
      @Qualifier("bookingsCredentials") Credentials credentials) {
    this.webClient = webClient;
    this.discoveryService = discoveryService;
    this.bookingsService = bookingsService;
    this.tokenService = tokenService;
    this.credentials = credentials;
  }

  @HystrixCommand(commandKey = "available-seats", groupKey = "airline-flights", commandProperties = {
      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
      @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "800"),
      @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
  })
  public Mono<TotalBooked> totalBooked(String flightId) {
    return discoveryService.serviceAddressFor(this.bookingsService).next()
        .flatMap(address -> Mono.just(this.webClient.mutate().baseUrl(address + "/flights" + flightId).build().get()))
        .flatMap(requestHeadersUriSpec ->
            Flux.combineLatest(Flux.just(requestHeadersUriSpec),Flux.from(tokenService.token(this.credentials)),(reqSpec, token) ->{
              reqSpec.header("Authorization","Bearer" + token.getToken());
              return reqSpec;
            })
            .next())
        .map(RequestHeadersSpec::retrieve)
        .flatMap(eq -> eq.bodyToMono(TotalBooked.class));
  }

}
