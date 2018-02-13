package springfive.airline.airlineflights.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import springfive.airline.airlineflights.domain.resource.data.FlightRequest;

@Data
@Document(collection = "flights")
public class Flight {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  @Id
  String id;

  Airport from;

  Airport to;

  LocalDateTime departureAt;

  LocalDateTime arriveAt;

  Plane plane;

  Set<Connection> connections;

  Set<RegularPrice> prices;

  @Builder
  public static Flight newFlight(Airport from,Airport to,String departureAt,String arriveAt,Plane plane,Set<Connection> connections,Set<RegularPrice> prices){
    Flight newFlight = new Flight();
    newFlight.from = from;
    newFlight.to = to;
    newFlight.departureAt = LocalDateTime.parse(departureAt, FORMATTER);
    newFlight.arriveAt = LocalDateTime.parse(arriveAt, FORMATTER);
    newFlight.plane = plane;
    newFlight.connections = connections;
    newFlight.prices = prices;
    return newFlight;
  }

  public Flight fromFlightRequest(FlightRequest flightRequest){
    this.from = flightRequest.getFrom();
    this.to = flightRequest.getTo();
    this.departureAt = LocalDateTime.parse(flightRequest.getDepartureAt(), FORMATTER);
    this.arriveAt = LocalDateTime.parse(flightRequest.getArriveAt(), FORMATTER);
    this.connections = flightRequest.getConnections();
    return this;
  }

}
