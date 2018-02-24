package springfive.airline.airlinebooking.domain.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import springfive.airline.airlinebooking.domain.Plane;
import springfive.airline.airlinebooking.domain.exception.PlaneNotFoundException;

@Service
public class PlaneService {

  private final WebClient webClient;

  private final String planesService;

  private final DiscoveryService discoveryService;

  public PlaneService(WebClient webClient, DiscoveryService discoveryService,
      @Value("${planes.service}") String planesService) {
    this.webClient = webClient;
    this.discoveryService = discoveryService;
    this.planesService = planesService;
  }

  @HystrixCommand(commandKey = "plane-by-id", groupKey = "airline-flights", commandProperties = {
      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
      @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "800"),
      @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
  })
  public Mono<Plane> plane(String id) {
    return discoveryService.serviceAddressFor(this.planesService).next().flatMap(
        address -> this.webClient.mutate().baseUrl(address + "/" + id).build().get().retrieve()
            .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                Mono.error(new PlaneNotFoundException(id))
            ).onStatus(HttpStatus::is5xxServerError, clientResponse ->
                Mono.error(new PlaneNotFoundException(id))
            ).bodyToMono(Plane.class));
  }

}
