package springfive.airline.airlinefare.domain.rules.spec.time;

import java.time.LocalDateTime;
import springfive.airline.airlinefare.domain.Flight;
import springfive.airline.airlinefare.domain.specification.AbstractSpecification;

public abstract class MonthsBeforeDeparture extends AbstractSpecification<Flight>{

  private final LocalDateTime requestedAt;

  private final Integer months;

  protected MonthsBeforeDeparture(LocalDateTime requestedAt, Integer months) {
    this.requestedAt = requestedAt;
    this.months = months;
  }

  @Override
  public boolean isSatisfiedBy(Flight flight) {
    final LocalDateTime validPeriod = flight.getDepartureAt().minusMonths(this.months);
    return requestedAt.isBefore(validPeriod);
  }

}
