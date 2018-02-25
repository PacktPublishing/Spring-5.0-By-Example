package springfive.airline.airlinefare.domain;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import springfive.airline.airlinefare.domain.flight.Flight;
import springfive.airline.airlinefare.domain.flight.FlightInfo;

@Data
public class Fare {

  String id;

  LocalDateTime validUntil;

  LocalDateTime createdAt;

  Set<Reservation> reservations;

  FlightInfo flight;

  @Builder
  public static Fare newFare(String id,LocalDateTime validUntil,Set<Reservation> reservations,FlightInfo flight){
    Fare fare = new Fare();
    fare.id = id;
    fare.validUntil = validUntil;
    fare.createdAt = LocalDateTime.now();
    fare.reservations = reservations;
    fare.flight = flight;
    return fare;
  }

}
