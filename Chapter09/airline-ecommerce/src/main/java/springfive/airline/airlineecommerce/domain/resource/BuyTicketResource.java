package springfive.airline.airlineecommerce.domain.resource;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import springfive.airline.airlineecommerce.domain.resource.data.BookingRequest;
import springfive.airline.airlineecommerce.domain.service.BookingService;

@RestController
@RequestMapping("/")
public class BuyTicketResource {

  private final BookingService bookingService;

  public BuyTicketResource(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @PostMapping("/tickets")
  public Mono<ResponseEntity<Void>> register(@Valid @RequestBody BookingRequest bookingRequest, UriComponentsBuilder uriBuilder){
    return this.bookingService.buyTicket(bookingRequest).map(data -> {
      URI location = uriBuilder.path("/bookings/{id}")
          .buildAndExpand(data.getId())
          .toUri();
      return ResponseEntity.created(location).build();
    });
  }

}
