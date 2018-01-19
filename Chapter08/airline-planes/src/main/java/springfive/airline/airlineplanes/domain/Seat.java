package springfive.airline.airlineplanes.domain;

import lombok.Data;

@Data
public class Seat {

  String identity;

  Integer row;

  Seat rightSide;

  Seat leftSide;

  Class category;

}
