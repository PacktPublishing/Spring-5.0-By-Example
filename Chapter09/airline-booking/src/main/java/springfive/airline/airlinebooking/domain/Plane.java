package springfive.airline.airlinebooking.domain;

import java.util.Set;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Plane {

  @Field("plane_id")
  String id;

  Set<Seat> seats;

  public Long seatsOfClass(String classId){
    return seats.stream().filter(seat -> seat.category.id.equalsIgnoreCase(classId)).count();
  }

  public boolean exist(Seat seat){
    return this.seats.stream().anyMatch(s -> s.equals(seat));

  }

}
