package springfive.airline.airlineflights.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import springfive.airline.airlineflights.domain.Plane;

@Service
public class PlaneService {

  private final WebClient webClient;

  private final String planesService;

  private final String planesServiceApiPath;

  private final DiscoveryService discoveryService;

  public PlaneService(WebClient webClient, DiscoveryService discoveryService,
      @Value("${planes.service}") String planesService,@Value("${planes.path}") String planesServiceApiPath) {
    this.webClient = webClient;
    this.discoveryService = discoveryService;
    this.planesService = planesService;
    this.planesServiceApiPath = planesServiceApiPath;
  }

  @HystrixCommand(commandKey = "plane-by-id",groupKey = "airline-flights",commandProperties = {
      @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),
      @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
  })
  public Mono<Plane> plane(String id) {
    return discoveryService.serviceAddressFor(this.planesService).next().flatMap(
        address -> this.webClient.mutate().baseUrl(address + "/" + this.planesServiceApiPath + "/" + id).build().get().exchange()
        .flatMap(clientResponse -> clientResponse.bodyToMono(Plane.class)));
  }

}
