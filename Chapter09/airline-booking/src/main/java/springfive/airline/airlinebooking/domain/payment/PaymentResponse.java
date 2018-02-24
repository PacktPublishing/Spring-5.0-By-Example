package springfive.airline.airlinebooking.domain.payment;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

  String id;

  String bookingId;

  String transactionId;

  BigDecimal value;

  PaymentStatus status;

}
