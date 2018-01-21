package springfive.airline.airlinebooking.domain;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import springfive.airline.airlinebooking.domain.fare.Fare;

@Data
@Document(collection = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

  @Id
  String id;

  Flight flight;

  Set<Seat> seats;

  Fare fare;

}
