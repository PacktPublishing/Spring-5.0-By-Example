package springfive.airline.airlinepayments.domain.data;

import java.time.LocalDateTime;
import lombok.Data;
import springfive.airline.airlinepayments.domain.Booking;

@Data
public class PaymentRequest {

  Booking booking;

  LocalDateTime requestedAt;


}
