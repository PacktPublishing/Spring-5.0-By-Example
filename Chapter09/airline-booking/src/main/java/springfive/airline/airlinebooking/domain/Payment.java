package springfive.airline.airlinebooking.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import springfive.airline.airlinebooking.domain.payment.PaymentStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

  @Field("payment_id")
  String id;

  String transactionId;

  BigDecimal value;

  PaymentStatus status;

}
