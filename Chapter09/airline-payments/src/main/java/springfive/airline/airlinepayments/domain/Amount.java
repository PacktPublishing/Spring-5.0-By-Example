package springfive.airline.airlinepayments.domain;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Amount {

  Currency currency;

  BigDecimal value;

}
