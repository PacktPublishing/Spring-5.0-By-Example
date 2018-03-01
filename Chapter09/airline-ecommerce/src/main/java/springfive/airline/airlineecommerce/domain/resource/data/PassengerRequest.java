package springfive.airline.airlineecommerce.domain.resource.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.Data;
import springfive.airline.airlineecommerce.domain.passenger.Address;
import springfive.airline.airlineecommerce.domain.passenger.Contact;
import springfive.airline.airlineecommerce.domain.passenger.PassengerDocument;

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
