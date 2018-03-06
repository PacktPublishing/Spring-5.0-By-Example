package springfive.airline.airlinebooking.domain.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfive.airline.airlinebooking.domain.Booking;
import springfive.airline.airlinebooking.domain.service.BookingService;

@RestController
@RequestMapping("/")
public class BoardingPassResource {

  private final BookingService bookingService;

  public BoardingPassResource(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping("/{id}/boarding-pass")
  public Mono<ResponseEntity<Booking>> boardingPass(@PathVariable("id")String id){
    return this.bookingService.byId(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
  }

}
