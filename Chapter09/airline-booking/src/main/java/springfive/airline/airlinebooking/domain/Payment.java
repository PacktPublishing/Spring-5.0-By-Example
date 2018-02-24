package springfive.airline.airlinebooking.domain;

import java.math.BigDecimal;
import lombok.Data;
import springfive.airline.airlinebooking.domain.payment.PaymentStatus;

@Data
public class Payment {

  String id;

  String transactionId;

  BigDecimal value;

  PaymentStatus status;

}
