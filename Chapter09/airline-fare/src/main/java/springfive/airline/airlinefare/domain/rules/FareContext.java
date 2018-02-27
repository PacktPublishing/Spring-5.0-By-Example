package springfive.airline.airlinefare.domain.rules;

import java.util.Set;
import lombok.Value;
import springfive.airline.airlinefare.domain.Booking;
import springfive.airline.airlinefare.domain.flight.Flight;
import springfive.airline.airlinefare.domain.Plane;
import springfive.airline.airlinefare.domain.Reservation;
import springfive.airline.airlinefare.domain.flight.FlightInfo;
import springfive.airline.airlinefare.domain.flight.FlightInfo.PlaneInfo;

@Value
public class FareContext {

  Flight flight;

  Set<Booking> bookings;

  Plane plane;

  Set<Reservation> reservations;

  public FlightInfo flightInfo(){
    return FlightInfo.builder().id(this.flight.getId()).number(this.flight.getNumber()).plane(
        PlaneInfo.builder().id(this.flight.getPlane().getId()).build()).build();
  }

}
