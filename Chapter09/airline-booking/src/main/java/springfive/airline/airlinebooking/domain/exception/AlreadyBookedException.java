package springfive.airline.airlinebooking.domain.exception;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import springfive.airline.airlinebooking.domain.Flight;
import springfive.airline.airlinebooking.domain.Seat;

@Value
@Builder
@EqualsAndHashCode(callSuper = false)
public class AlreadyBookedException extends RuntimeException {

  Flight flight;

  Seat seat;

}
