package springfive.airline.airlineflights.domain.resource.data;

import lombok.Data;

@Data
public class FlightQuery {

  String from;

  String to;

  String departureAt;

  String arriveAt;

}
