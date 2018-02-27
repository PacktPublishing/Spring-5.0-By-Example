package springfive.airline.airlinebooking.domain.fare;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Passenger {

  @Field("passenger_id")
  String id;

  String name;

  String email;

  String fidelityNumber;

}
