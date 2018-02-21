package springfive.airline.airlineecommerce.domain.flight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import lombok.Data;

@Data
public class Flight {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  String id;

  Airport from;

  Airport to;

  LocalDateTime departureAt;

  LocalDateTime arriveAt;

  Plane plane;

  Set<Connection> connections;

}
