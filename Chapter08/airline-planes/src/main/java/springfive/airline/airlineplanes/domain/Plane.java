package springfive.airline.airlineplanes.domain;

import java.util.Set;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "planes")
public class Plane {

  @Id
  String id;

  String owner;

  PlaneModel model;

  Set<Seat> seats;

  String notes;

}
