package springfive.airline.airlinebooking.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Flight {

  String id;

  String number;

  LocalDateTime departureAt;

  LocalDateTime arriveAt;

  Plane plane;

}
