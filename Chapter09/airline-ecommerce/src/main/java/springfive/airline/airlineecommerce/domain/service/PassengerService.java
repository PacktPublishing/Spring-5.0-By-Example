package springfive.airline.airlineecommerce.domain.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlineecommerce.domain.passenger.Passenger;
import springfive.airline.airlineecommerce.domain.resource.data.PassengerRequest;
import springfive.airline.airlineecommerce.infra.oauth.Credentials;

@Service
public class PassengerService {

  private static final transient DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

  private final WebClient webClient;

  private final DiscoveryService discoveryService;

  private final TokenService tokenService;

  private final Credentials passengersCredentials;

  private final String passengersService;

  public PassengerService(WebClient webClient,
      DiscoveryService discoveryService,
      TokenService tokenService,
      @Qualifier("passengersCredentials")Credentials passengersCredentials,
      @Value("${passengers.service}")String passengersService) {
    this.webClient = webClient;
    this.discoveryService = discoveryService;
    this.tokenService = tokenService;
    this.passengersCredentials = passengersCredentials;
    this.passengersService = passengersService;
  }


  @HystrixCommand(commandKey = "create-passenger",groupKey = "ecommerce-operations",commandProperties = {
      @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
      @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
      @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
  })
  public Mono<Passenger> newPassenger(@NonNull PassengerRequest passengerRequest){
    return discoveryService.serviceAddressFor(this.passengersService).next()
        .flatMap(address -> Mono
            .just(this.webClient.mutate().baseUrl(address).build().post().body(BodyInserters.fromObject(passengerRequest))))
        .flatMap(requestHeadersUriSpec ->
            Flux.combineLatest(Flux.just(requestHeadersUriSpec),Flux.from(tokenService.token(this.passengersCredentials)),(reqSpec, token) ->{
              reqSpec.header("Authorization","Bearer" + token.getToken());
              return reqSpec;
            })
                .next())
        .map(RequestHeadersSpec::retrieve)
        .flatMap(res -> res.bodyToMono(Passenger.class));
  }

}
