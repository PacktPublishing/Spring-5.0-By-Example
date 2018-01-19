package springfive.airline.airlinefare.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Reservation {

  Passenger passenger;

  Seat seat;

  BigDecimal price;

}
