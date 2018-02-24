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
import springfive.airline.airlinebooking.domain.Plane;
import springfive.airline.airlinebooking.domain.fare.Fare;
import springfive.airline.airlinebooking.infra.oauth.Credentials;

@Service
public class PlaneService {

  private final WebClient webClient;

  private final String planesService;

  private final DiscoveryService discoveryService;

  private final TokenService tokenService;

  private final Credentials credentials;

  public PlaneService(WebClient webClient, DiscoveryService discoveryService,
      @Value("${planes.service}") String planesService,
      TokenService tokenService,
      @Qualifier("planesCredentials") Credentials credentials) {
    this.webClient = webClient;
    this.discoveryService = discoveryService;
    this.planesService = planesService;
    this.tokenService = tokenService;
    this.credentials = credentials;
  }

  @HystrixCommand(commandKey = "plane-by-id", groupKey = "airline-booking", commandProperties = {
      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
      @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "800"),
      @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
  })
  public Mono<Plane> plane(String id) {
    return discoveryService.serviceAddressFor(this.planesService).next()
        .flatMap(address -> Mono.just(this.webClient.mutate().baseUrl(address + "/" + id).build().get()))
        .flatMap(requestHeadersUriSpec ->
            Flux.combineLatest(Flux.just(requestHeadersUriSpec),Flux.from(tokenService.token(this.credentials)),(reqSpec, token) ->{
              reqSpec.header("Authorization","Bearer" + token.getToken());
              return reqSpec;
            })
                .next())
        .map(RequestHeadersSpec::retrieve)
        .flatMap(eq -> eq.bodyToMono(Plane.class));
  }

}
