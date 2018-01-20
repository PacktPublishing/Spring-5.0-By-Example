package springfive.airline.airlinefare.domain;

import java.util.Set;
import lombok.Data;

@Data
public class Plane {

  String id;

  Set<Seat> seats;

  public Long seatsOfClass(String classId){
    return seats.stream().filter(seat -> seat.category.id.equalsIgnoreCase(classId)).count();
  }

}
