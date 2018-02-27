package springfive.airline.airlinebooking.domain;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Flight {

  @Field("flight_id")
  String id;

  String number;

  LocalDateTime departureAt;

  LocalDateTime arriveAt;

  Plane plane;

}
