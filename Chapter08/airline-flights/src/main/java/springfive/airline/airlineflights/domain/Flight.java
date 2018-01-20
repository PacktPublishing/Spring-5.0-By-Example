package springfive.airline.airlineflights.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import springfive.airline.airlineflights.resource.data.FlightRequest;

import javax.swing.*;

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

  public Flight fromFlightRequest(FlightRequest flightRequest){
    this.from = flightRequest.getFrom();
    this.to = flightRequest.getTo();
    this.departureAt = LocalDateTime.parse(flightRequest.getDepartureAt(), FORMATTER);
    this.arriveAt = LocalDateTime.parse(flightRequest.getArriveAt(), FORMATTER);
    this.connections = flightRequest.getConnections();
    return this;
  }

}