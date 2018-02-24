package springfive.airline.airlinebooking.domain;

import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import springfive.airline.airlinebooking.domain.fare.Fare;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings")
public class Booking {

  @Id
  String id;

  Flight flight;

  Set<Seat> seats;

  Fare fare;

  public Long booked(){
    return Objects.isNull(seats) ? 0L : seats.size();
  }

}
