package springfive.airline.airlineflights.resource.data;

import lombok.Data;
import springfive.airline.airlineflights.domain.Airport;
import springfive.airline.airlineflights.domain.Connection;

import java.util.Set;

@Data
public class FlightRequest {

    Airport from;

    Airport to;

    String departureAt;

    String arriveAt;

    String planeId;

    Set<Connection> connections;

}
