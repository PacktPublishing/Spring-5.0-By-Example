package springfive.airline.airlinepassengers.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "passengers")
public class Passenger {

    @Id
    String id;

    String name;

    String lastName;

    Set<PassengerDocument> documents;

    Set<Address> addresses;

    LocalDateTime bornDate;

    String fidelityNumber;

}
