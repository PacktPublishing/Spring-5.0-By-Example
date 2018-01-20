package springfive.airline.airlinefare.domain.rules;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import springfive.airline.airlinefare.domain.Flight;
import springfive.airline.airlinefare.domain.rules.spec.time.OneMonth;
import springfive.airline.airlinefare.domain.rules.spec.time.ThreeMonths;
import springfive.airline.airlinefare.domain.rules.spec.time.TwoMonths;

@Value
@Builder
public class TimeTaxRule implements Rule<Flight> {

  LocalDateTime requestedAt;

  @Override
  public BigDecimal tax(Flight flight) {
    if (new ThreeMonths(this.requestedAt).isSatisfiedBy(flight)) {
      return BigDecimal.valueOf(1);
    } else if (new TwoMonths(this.requestedAt).isSatisfiedBy(flight)) {
      return BigDecimal.valueOf(10);
    } else if (new OneMonth(this.requestedAt).isSatisfiedBy(flight)) {
      return BigDecimal.valueOf(15);
    } else {
      throw new IllegalArgumentException("Invalid time to calculate fare");
    }
  }

}
