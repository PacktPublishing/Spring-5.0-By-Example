package springfive.airline.airlineplanes.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SeatIdentity {

  @JsonProperty("seat_identity")
  String seatIdentity;

}
