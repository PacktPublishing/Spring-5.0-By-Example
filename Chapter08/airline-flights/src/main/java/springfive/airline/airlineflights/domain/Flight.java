package springfive.airline.airlineflights.domain;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "flights")
public class Flight {

  @Id
  String id;

  Airport from;

  Airport to;

  LocalDateTime departureAt;

  LocalDateTime arriveAt;

  Plane plane;

  Set<Connection> connections;

}
