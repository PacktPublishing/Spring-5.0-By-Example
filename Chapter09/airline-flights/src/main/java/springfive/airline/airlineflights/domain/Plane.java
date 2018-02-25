package springfive.airline.airlineflights.domain;

import java.util.Objects;
import java.util.Set;
import lombok.Data;

@Data
public class Plane {

  String id;

  PlaneModel model;

  Set<Seat> seats;

  public Long numberOfAvailableSeats(){
    return Objects.isNull(this.seats) ? 0L : seats.size();
  }

}
