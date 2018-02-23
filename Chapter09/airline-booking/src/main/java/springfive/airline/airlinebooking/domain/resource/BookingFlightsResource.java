package springfive.airline.airlinebooking.domain.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfive.airline.airlinebooking.domain.service.BookingService;
import springfive.airline.airlinebooking.domain.service.data.TotalBooked;

@RestController
@RequestMapping("/flights")
public class BookingFlightsResource {

  private final BookingService bookingService;

  public BookingFlightsResource(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping("/{flightId}/total")
  public Mono<TotalBooked> totalBooked(@PathVariable("flightId")String flightId){
    return this.bookingService.totalBooked(flightId);
  }

}
