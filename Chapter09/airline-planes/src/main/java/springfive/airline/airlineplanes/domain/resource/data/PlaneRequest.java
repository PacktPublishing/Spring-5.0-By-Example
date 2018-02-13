package springfive.airline.airlineplanes.domain.resource.data;

import java.util.Set;
import lombok.Data;
import springfive.airline.airlineplanes.domain.PlaneModel;
import springfive.airline.airlineplanes.domain.Seat;

@Data
public class PlaneRequest {

  String owner;

  PlaneModel model;

  Set<Seat> seats;

  String notes;

}
