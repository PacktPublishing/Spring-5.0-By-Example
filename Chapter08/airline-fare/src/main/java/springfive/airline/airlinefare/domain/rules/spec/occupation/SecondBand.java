package springfive.airline.airlinefare.domain.rules.spec.occupation;

import java.util.Set;
import springfive.airline.airlinefare.domain.Booking;
import springfive.airline.airlinefare.domain.Reservation;

public class SecondBand extends AbstractBandRule{

  public SecondBand(Set<Booking> bookings, Reservation reservation) {
    super(bookings, reservation, new Band(33.1d,66d));
  }

}
