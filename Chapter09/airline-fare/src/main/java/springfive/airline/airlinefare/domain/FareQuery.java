package springfive.airline.airlinefare.domain;

import java.util.Set;
import lombok.Data;

@Data
public class FareQuery {

  Passenger mainPassenger;

  Set<Reservation> reservations;

  Flight flight;

}
