package springfive.airline.airlinefare.domain;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class Fare {

  String id;

  LocalDateTime validUntil;

  LocalDateTime createdAt;

  Passenger mainPassenger;

  Set<Reservation> reservations;

  Flight flight;

}
