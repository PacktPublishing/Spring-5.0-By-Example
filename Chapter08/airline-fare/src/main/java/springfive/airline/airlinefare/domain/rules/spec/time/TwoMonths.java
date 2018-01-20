package springfive.airline.airlinefare.domain.rules.spec.time;

import java.time.LocalDateTime;

public class TwoMonths extends MonthsBeforeDeparture{

  public TwoMonths(LocalDateTime requestedAt) {
    super(requestedAt, 2);
  }

}
