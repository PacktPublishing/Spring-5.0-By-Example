package springfive.airline.airlineecommerce.domain.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import springfive.airline.airlineecommerce.infra.oauth.AccessToken;
import springfive.airline.airlineecommerce.infra.oauth.Credentials;

@Service
public class TokenService {

  private final WebClient webClient;


  private final String authService;

  private final String authServiceApiPath;

  private final DiscoveryService discoveryService;

  public TokenService(WebClient webClient,
      @Value("${auth.service}") String authService,
      @Value("${auth.path}") String authServiceApiPath,
      DiscoveryService discoveryService) {
    this.webClient = webClient;
    this.authService = authService;
    this.authServiceApiPath = authServiceApiPath;
    this.discoveryService = discoveryService;
  }

  @HystrixCommand(commandKey = "request-token",groupKey = "auth-operations",commandProperties = {
      @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
      @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
      @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
  })
  public Mono<AccessToken> token(Credentials credentials) {
    val authorizationHeader = Base64Utils.encodeToString(( credentials.getClientId() + ":" + credentials.getClientSecret()).getBytes());
    return discoveryService.serviceAddressFor(this.authService).next().flatMap(address ->
        this.webClient.mutate().baseUrl(address + "/" + this.authServiceApiPath).build()
        .post()
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .header("Authorization","Basic " + authorizationHeader)
        .body(BodyInserters.fromFormData("grant_type", "client_credentials"))
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, clientResponse ->
            Mono.error(new RuntimeException("Invalid call"))
        ).onStatus(HttpStatus::is5xxServerError, clientResponse ->
        Mono.error(new RuntimeException("Error on server"))
    ).bodyToMono(AccessToken.class));
  }

}
