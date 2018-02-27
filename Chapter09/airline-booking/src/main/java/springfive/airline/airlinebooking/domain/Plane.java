package springfive.airline.airlinebooking.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Set;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@JsonInclude(Include.NON_NULL)
public class Plane {

  @Field("plane_id")
  String id;

  @Transient
  Set<Seat> seats;

  public boolean exist(Seat seat){
    return this.seats.stream().anyMatch(s -> s.equals(seat));

  }

}
