package springfive.airline.airlinebooking.domain.fare;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@JsonInclude(Include.NON_NULL)
public class Passenger {

  @Field("passenger_id")
  String id;

  String name;

  String email;

  String fidelityNumber;

}
