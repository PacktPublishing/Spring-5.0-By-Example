package springfive.airline.airlinepayments.domain.data;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PaymentResponse {

  String paymentId;

  LocalDateTime executedAt;

}
