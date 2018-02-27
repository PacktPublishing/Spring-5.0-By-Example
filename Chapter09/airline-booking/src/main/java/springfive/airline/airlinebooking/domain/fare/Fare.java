package springfive.airline.airlinebooking.domain.fare;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import springfive.airline.airlinebooking.domain.Flight;

@Data
public class Fare {

  @Field("fare_id")
  String id;

  LocalDateTime validUntil;

  LocalDateTime createdAt;

  Set<Reservation> reservations;

  Flight flight;

  PlaneInfo plane;

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

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonInclude(Include.NON_NULL)
  public static class PlaneInfo{

    private String id;

  }


}
