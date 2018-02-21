package springfive.airline.airlineecommerce.domain;

import lombok.Data;

@Data
public class FlightSearch {

  String from;

  String to;

  String departureAt;

  String arriveAt;

  Integer passengers;

}
