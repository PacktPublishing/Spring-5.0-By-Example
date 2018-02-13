package springfive.airline.airlinefare.domain.rules.spec.time;

import java.time.LocalDateTime;

public class ThreeMonths extends MonthsBeforeDeparture{

  public ThreeMonths(LocalDateTime requestedAt) {
    super(requestedAt, 3);
  }

}
