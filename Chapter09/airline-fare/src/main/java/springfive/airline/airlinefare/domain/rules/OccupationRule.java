package springfive.airline.airlinefare.domain.rules;

import java.math.BigDecimal;
import java.util.Set;
import lombok.Builder;
import springfive.airline.airlinefare.domain.Booking;
import springfive.airline.airlinefare.domain.Plane;
import springfive.airline.airlinefare.domain.Reservation;
import springfive.airline.airlinefare.domain.rules.spec.occupation.FirstBand;
import springfive.airline.airlinefare.domain.rules.spec.occupation.SecondBand;
import springfive.airline.airlinefare.domain.rules.spec.occupation.ThirdBand;

@Builder
public class OccupationRule implements Rule<Plane> {

  private final Set<Booking> bookings;

  private final Set<Reservation> reservations;

  public OccupationRule(Set<Booking> bookings, Set<Reservation> reservations) {
    this.bookings = bookings;
    this.reservations = reservations;
  }

  @Override
  public BigDecimal tax(Plane plane) {
    Double total = 0d;
    for (Reservation reservation : reservations) {
      if (new FirstBand(this.bookings, reservation).isSatisfiedBy(plane)) {
        total += 0d;
      } else if (new SecondBand(this.bookings, reservation).isSatisfiedBy(plane)) {
        total += 10d;
      } else if (new ThirdBand(this.bookings, reservation).isSatisfiedBy(plane)) {
        total += 15d;
      } else {
        throw new IllegalArgumentException("Invalid tax");
      }
    }
    return BigDecimal.valueOf(total / reservations.size());
  }

}
