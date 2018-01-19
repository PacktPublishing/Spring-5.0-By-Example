package springfive.airline.airlineflights.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Connection {

  Airport from;

  Airport to;

  LocalDateTime departureAt;

  LocalDateTime arriveAt;

}
