package springfive.airline.airlinebooking.domain.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import springfive.airline.airlinebooking.domain.Booking;

public interface BookingRepository extends ReactiveCrudRepository<Booking,String> {

  Flux<Booking> findByFlightId(String flightId);

}
