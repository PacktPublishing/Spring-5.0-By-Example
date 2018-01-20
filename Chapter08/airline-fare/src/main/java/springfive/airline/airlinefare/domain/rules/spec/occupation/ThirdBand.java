package springfive.airline.airlinefare.domain.rules.spec.occupation;

import java.util.Set;
import springfive.airline.airlinefare.domain.Booking;
import springfive.airline.airlinefare.domain.Reservation;

public class ThirdBand extends AbstractBandRule{

  public ThirdBand(Set<Booking> bookings, Reservation reservation) {
    super(bookings, reservation, new Band(66.1d,99.9d));
  }

}
