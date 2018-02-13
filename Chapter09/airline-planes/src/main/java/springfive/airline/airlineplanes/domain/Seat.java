package springfive.airline.airlineplanes.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Seat {

  String identity;

  Integer row;

  @JsonProperty("right_side")
  SeatIdentity rightSide;

  @JsonProperty("left_side")
  SeatIdentity leftSide;

  Class category;

}
