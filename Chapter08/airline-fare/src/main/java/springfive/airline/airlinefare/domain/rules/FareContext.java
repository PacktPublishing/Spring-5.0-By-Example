package springfive.airline.airlinefare.domain.rules;

import java.util.Set;
import lombok.Value;
import springfive.airline.airlinefare.domain.Booking;
import springfive.airline.airlinefare.domain.Flight;
import springfive.airline.airlinefare.domain.Plane;
import springfive.airline.airlinefare.domain.Reservation;

@Value
public class FareContext {

  Flight flight;

  Set<Booking> bookings;

  Plane plane;

  Set<Reservation> reservations;

}
