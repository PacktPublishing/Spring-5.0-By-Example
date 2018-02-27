package springfive.airline.airlinebooking.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@JsonInclude(Include.NON_NULL)
public class Flight {

  @Field("flight_id")
  String id;

  String number;

  LocalDateTime departureAt;

  LocalDateTime arriveAt;

  Plane plane;

}
