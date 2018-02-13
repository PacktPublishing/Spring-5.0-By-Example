package springfive.airline.airlineflights.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Connection {

  Airport from;

  Airport to;

  @JsonProperty("departure_at")
  LocalDateTime departureAt;

  @JsonProperty("arrive_at")
  LocalDateTime arriveAt;

  Integer order;

}
