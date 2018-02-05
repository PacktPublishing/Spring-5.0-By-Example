package springfive.airline.airlineflights.domain.resource.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import springfive.airline.airlineflights.domain.Airport;
import springfive.airline.airlineflights.domain.Connection;

import java.util.Set;
import springfive.airline.airlineflights.domain.RegularPrice;

@Data
public class FlightRequest {

  Airport from;

  Airport to;

  @JsonProperty("departure_at")
  String departureAt;

  @JsonProperty("arrive_at")
  String arriveAt;

  @JsonProperty("plane_id")
  String planeId;

  Set<Connection> connections;

  Set<RegularPrice> prices;

}
