package springfive.airline.airlinebooking.domain.resource;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlinebooking.domain.Booking;
import springfive.airline.airlinebooking.domain.resource.data.BookingRequest;
import springfive.airline.airlinebooking.domain.service.BookingService;

@RestController
@RequestMapping("/")
public class BookingResource {

  private final BookingService bookingService;

  public BookingResource(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping("/{flightId}/bookings")
  public Flux<Booking> bookingsOfFlight(@PathVariable("flightId")String flightId){
    return this.bookingService.bookingsOfFlight(flightId);
  }

  @PostMapping
  public Mono<ResponseEntity<Void>> newFlight(@Valid @RequestBody BookingRequest bookingRequest, UriComponentsBuilder uriBuilder){
    return this.bookingService.newBooking(bookingRequest.getFareId()).map(data -> {
      URI location = uriBuilder.path("/bookings/{id}")
          .buildAndExpand(data.getId())
          .toUri();
      return ResponseEntity.created(location).build();
    });
  }

}
