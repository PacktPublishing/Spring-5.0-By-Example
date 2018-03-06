package springfive.airline.airlineecommerce.domain.passenger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfive.airline.airlineecommerce.domain.resource.data.PassengerRequest;

@Data
@NoArgsConstructor
public class Passenger {

  private static final transient DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

  String id;

  String name;

  String lastName;

  String fidelityNumber;

  Set<PassengerDocument> documents;

  Address address;

  LocalDate bornDate;

  Contact contact;

  @Builder
  public static Passenger newPassenger(String name,String lastName,String fidelityNumber,Set<PassengerDocument> documents,Address address,String bornDate,Contact contact){
    final Passenger passenger = new Passenger();
    passenger.setName(name);
    passenger.setLastName(lastName);
    passenger.setFidelityNumber(fidelityNumber);
    passenger.setContact(contact);
    passenger.setAddress(address);
    passenger.setDocuments(documents);
    passenger.setBornDate(LocalDate.parse(bornDate,FORMATTER));
    return passenger;
  }

  public Passenger fromPassengerRequest(PassengerRequest passengerRequest){
    this.name = passengerRequest.getName();
    this.lastName = passengerRequest.getLastName();
    this.fidelityNumber = passengerRequest.getFidelityNumber();
    this.contact  = passengerRequest.getContact();
    this.address = passengerRequest.getAddress();
    this.documents = passengerRequest.getDocuments();
    this.bornDate = LocalDate.parse(passengerRequest.getBornDate(),FORMATTER);
    return this;
  }

}
