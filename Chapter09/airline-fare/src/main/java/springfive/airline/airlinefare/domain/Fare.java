package springfive.airline.airlinefare.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Data;

@Data
public class Fare {

  String id;

  LocalDateTime validUntil;

  LocalDateTime createdAt;

  Set<Reservation> reservations;

  Flight flight;

  @Builder
  public static Fare newFare(String id,LocalDateTime validUntil,Set<Reservation> reservations,Flight flight){
    Fare fare = new Fare();
    fare.id = id;
    fare.validUntil = validUntil;
    fare.createdAt = LocalDateTime.now();
    fare.reservations = reservations;
    fare.flight = flight;
    return fare;
  }

  public BigDecimal getTotal(){
    return this.reservations.stream().map(res -> res.price)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

}
