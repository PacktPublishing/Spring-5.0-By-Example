package springfive.airline.airlineflights.domain;

import lombok.Data;

@Data
public class Seat {

  String identity;

  Integer row;

  Class category;

}
