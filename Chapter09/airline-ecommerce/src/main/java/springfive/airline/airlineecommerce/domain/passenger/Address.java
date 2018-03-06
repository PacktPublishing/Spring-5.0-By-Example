package springfive.airline.airlineecommerce.domain.passenger;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Address {

  @JsonProperty("address_1")
  String address1;

  @JsonProperty("address_2")
  String address2;

  String city;

  String state;

  String country;

}
