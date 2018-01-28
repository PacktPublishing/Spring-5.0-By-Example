package springfive.airline.airlinepayments.domain.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import springfive.airline.airlinepayments.domain.data.BookingUpdateRequest;

@Service
public class BookingNotifier {

  public Mono<BookingUpdateRequest> updateBooking(@NonNull BookingUpdateRequest updateRequest){
    return Mono.empty();
  }

}
