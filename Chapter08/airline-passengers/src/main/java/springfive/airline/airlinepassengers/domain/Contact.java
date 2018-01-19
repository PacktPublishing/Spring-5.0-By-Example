package springfive.airline.airlinepassengers.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Contact {

  String email;

  @JsonProperty("phone_1")
  String phone1;

  @JsonProperty("phone_2")
  String phone2;

}
