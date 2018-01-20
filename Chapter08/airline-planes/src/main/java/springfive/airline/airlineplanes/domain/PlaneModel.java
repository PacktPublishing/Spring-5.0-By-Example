package springfive.airline.airlineplanes.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlaneModel {

  String factory;

  String model;

  String name;

  @JsonProperty("reference_name")
  String referenceName;

}
