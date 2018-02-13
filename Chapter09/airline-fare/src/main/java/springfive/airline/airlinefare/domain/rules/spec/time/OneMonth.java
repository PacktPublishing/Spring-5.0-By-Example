package springfive.airline.airlinefare.domain.rules.spec.time;

import java.time.LocalDateTime;

public class OneMonth extends MonthsBeforeDeparture{

  public OneMonth(LocalDateTime requestedAt) {
    super(requestedAt, 1);
  }

}
