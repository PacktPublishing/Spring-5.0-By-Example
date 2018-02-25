package springfive.airline.airlinefare.domain.rules.spec.occupation;

import java.util.Set;
import springfive.airline.airlinefare.domain.Booking;
import springfive.airline.airlinefare.domain.Plane;
import springfive.airline.airlinefare.domain.Reservation;
import springfive.airline.airlinefare.domain.specification.AbstractSpecification;

public class AbstractBandRule extends AbstractSpecification<Plane>{

  private final Set<Booking> bookings;

  private final Reservation reservation;

  private final Band targetBand;

  public AbstractBandRule(Set<Booking> bookings, Reservation reservation,
      Band targetBand) {
    this.bookings = bookings;
    this.reservation = reservation;
    this.targetBand = targetBand;
  }

  @Override
  public boolean isSatisfiedBy(Plane plane) {
    Long totalSeats = plane.seatsOfClass(reservation.getSeat().categoryId());
    Long occupiedSeats = bookingsOfClass(reservation.getSeat().categoryId());
    final Long percentage = occupiedSeats == 0 ? 0 : totalSeats / occupiedSeats;
    return this.targetBand.inside(Double.valueOf(percentage.toString()));
  }

  private Long bookingsOfClass(String classId){
    return bookings.stream()
        .filter(booking -> booking.reservationsOfClass(classId) > 0)
        .count();
  }

}
