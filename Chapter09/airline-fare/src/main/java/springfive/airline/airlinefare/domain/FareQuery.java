package springfive.airline.airlinefare.domain;

import java.util.Set;
import lombok.Data;
import springfive.airline.airlinefare.domain.flight.FlightRequest;

@Data
public class FareQuery {

  Passenger mainPassenger;

  Set<Reservation> reservations;

  FlightRequest flight;

}
