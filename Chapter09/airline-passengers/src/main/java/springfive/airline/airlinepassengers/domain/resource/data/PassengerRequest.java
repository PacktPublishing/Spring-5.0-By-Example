package springfive.airline.airlinepassengers.domain.resource.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.Data;
import springfive.airline.airlinepassengers.domain.Address;
import springfive.airline.airlinepassengers.domain.Contact;
import springfive.airline.airlinepassengers.domain.PassengerDocument;

@Data
public class PassengerRequest {

  @JsonProperty("fidelity_number")
  String fidelityNumber;

  String name;

  @JsonProperty("last_name")
  String lastName;

  Set<PassengerDocument> documents;

  @JsonProperty("born_date")
  String bornDate;

  Address address;

  Contact contact;

}
