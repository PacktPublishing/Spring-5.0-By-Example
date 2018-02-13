package springfive.airline.airlinefare.domain;

import lombok.Data;

@Data
public class Passenger {

  String id;

  String name;

  String email;

  String fidelityNumber;

  PassengerDocument document;

}
