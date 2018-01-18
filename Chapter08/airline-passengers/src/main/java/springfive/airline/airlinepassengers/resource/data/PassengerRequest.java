package springfive.airline.airlinepassengers.resource.data;

import lombok.Data;
import springfive.airline.airlinepassengers.domain.PassengerDocument;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PassengerRequest {

    String name;

    String lastName;

    Set<PassengerDocument> documents;

    LocalDateTime bornDate;

    String fidelityNumber;

}
