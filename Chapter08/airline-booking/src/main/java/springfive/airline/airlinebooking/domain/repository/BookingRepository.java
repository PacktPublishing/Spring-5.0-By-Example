package springfive.airline.airlinebooking.domain.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import springfive.airline.airlinebooking.domain.Booking;

public interface BookingRepository extends ReactiveCrudRepository<Booking,String> {

}
