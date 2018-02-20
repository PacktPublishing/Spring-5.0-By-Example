package springfive.airline.airlinebooking.domain.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlinebooking.domain.Booking;
import springfive.airline.airlinebooking.domain.service.BookingService;
import springfive.airline.airlinebooking.domain.service.data.TotalBooked;

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

  @GetMapping("/{flightId}/total")
  public Mono<TotalBooked> totalBooked(@PathVariable("flightId")String flightId){
    return this.bookingService.totalBooked(flightId);
  }

}
