package springfive.airline.airlinefare.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
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

  public Mono<Set<Booking>> bookingOfFlight(String bookingId) {
    return discoveryService.serviceAddressFor(this.bookingService).next()
        .flatMap(address -> Mono.just(this.webClient.mutate().baseUrl(address + "/" + bookingId).build().get()))
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
