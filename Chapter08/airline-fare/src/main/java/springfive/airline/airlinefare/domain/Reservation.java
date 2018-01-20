package springfive.airline.airlinefare.domain;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
public class Reservation {

  Passenger passenger;

  Seat seat;

  BigDecimal price;

  @Builder
  public static Reservation newReservation(Passenger passenger,Seat seat,BigDecimal price){
    final Reservation reservation = new Reservation();
    reservation.passenger = passenger;
    reservation.seat = seat;
    reservation.price = price;
    return reservation;
  }

}
