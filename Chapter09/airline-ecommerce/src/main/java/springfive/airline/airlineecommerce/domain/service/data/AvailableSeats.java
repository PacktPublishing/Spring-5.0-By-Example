package springfive.airline.airlineecommerce.domain.service.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailableSeats {

  Long available;

  String flightId;

}
