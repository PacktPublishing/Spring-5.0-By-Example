package springfive.airline.airlinefare.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import springfive.airline.airlinefare.domain.Booking;

@Service
public class BookingService {

  private final DiscoveryService discoveryService;

  private final String bookingService;

  private final String bookingServiceApiPath;

  private final WebClient webClient;

  private final ObjectMapper mapper;

  public BookingService(DiscoveryService discoveryService,
      @Value("${bookings.service}") String bookingService,
      @Value("${bookings.path}") String bookingServiceApiPath,
      WebClient webClient, ObjectMapper mapper) {
    this.discoveryService = discoveryService;
    this.bookingService = bookingService;
    this.bookingServiceApiPath = bookingServiceApiPath;
    this.webClient = webClient;
    this.mapper = mapper;
  }

  public Mono<Set<Booking>> bookingOfFlight(String bookingId) {
    return discoveryService.serviceAddressFor(this.bookingService).next().flatMap(
        address -> this.webClient.mutate()
            .baseUrl(address + "/" + this.bookingServiceApiPath + "/" + bookingId).build().get()
            .exchange()
            .flatMap(clientResponse ->
                {
                  final CollectionType type = mapper.getTypeFactory().
                      constructCollectionType(Set.class, Booking.class);
                  return clientResponse.bodyToMono(ParameterizedTypeReference.forType(type));
                }
            ));
  }

}
