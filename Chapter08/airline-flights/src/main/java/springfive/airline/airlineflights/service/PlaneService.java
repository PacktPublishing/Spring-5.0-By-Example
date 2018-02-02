package springfive.airline.airlineflights.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.tuple.Tuple;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;
import springfive.airline.airlineflights.domain.Plane;
import springfive.airline.airlineflights.exception.PlaneNotFoundException;

import java.util.function.BiFunction;

@Service
public class PlaneService {

  private final WebClient webClient;

  private final String planesService;

  private final String planesServiceApiPath;

  private final DiscoveryService discoveryService;

  private final TokenService tokenService;

  public PlaneService(WebClient webClient, DiscoveryService discoveryService,TokenService tokenService,
      @Value("${planes.service}") String planesService,
      @Value("${planes.path}") String planesServiceApiPath) {
    this.webClient = webClient;
    this.discoveryService = discoveryService;
    this.planesService = planesService;
    this.planesServiceApiPath = planesServiceApiPath;
    this.tokenService = tokenService;
  }

  @HystrixCommand(commandKey = "plane-by-id",groupKey = "airline-flights",fallbackMethod = "fallback",commandProperties = {
      @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
      @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "800"),
      @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
  })
  public Mono<Plane> plane(String id) {
    return discoveryService.serviceAddressFor(this.planesService).next()
            .flatMap(address -> Mono.just(this.webClient.mutate().baseUrl(address + "/" + this.planesServiceApiPath + "/" + id).build().get()))
            .flatMap(requestHeadersUriSpec ->
              Flux.combineLatest(Flux.just(requestHeadersUriSpec),Flux.from(tokenService.token()),(reqSpec, token) ->{
                reqSpec.header("Authorization","Bearer" + token.getToken());
              return reqSpec;
              })
              .next())
            .map(WebClient.RequestHeadersSpec::retrieve)
            .flatMap(eq -> eq.bodyToMono(Plane.class));
  }

  public Mono<Plane> fallback(String id){

    System.err.println("===============");
    System.err.println("===============");
    System.err.println("===============");
    return Mono.empty();

  }

}
