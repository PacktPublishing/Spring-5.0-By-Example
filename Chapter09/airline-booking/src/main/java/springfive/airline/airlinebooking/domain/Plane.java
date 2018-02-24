package springfive.airline.airlinebooking.domain;

import java.util.Set;
import lombok.Data;

@Data
public class Plane {

  String id;

  Set<Seat> seats;

  public Long seatsOfClass(String classId){
    return seats.stream().filter(seat -> seat.category.id.equalsIgnoreCase(classId)).count();
  }

  public boolean exist(Seat seat){
    return this.seats.stream().anyMatch(s -> s.equals(seat));

  }

}
