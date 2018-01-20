package springfive.airline.airlinebooking.domain.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfive.airline.airlinebooking.domain.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingResource {

  private final BookingService bookingService;

  public BookingResource(BookingService bookingService) {
    this.bookingService = bookingService;
  }


}
