package springfive.airline.airlinefare.domain.rules.spec.occupation;

import java.util.Set;
import springfive.airline.airlinefare.domain.Booking;
import springfive.airline.airlinefare.domain.Reservation;

public class FirstBand extends AbstractBandRule{

  public FirstBand(Set<Booking> bookings, Reservation reservation) {
    super(bookings, reservation, new Band(0d,33d));
  }

}
