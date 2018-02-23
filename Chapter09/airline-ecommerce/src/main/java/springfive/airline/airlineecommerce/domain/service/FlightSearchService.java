package springfive.airline.airlineecommerce.domain.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import springfive.airline.airlineecommerce.domain.FlightSearch;
import springfive.airline.airlineecommerce.domain.flight.Flight;

@Service
public class FlightSearchService {

  private final FlightService flightService;

  public FlightSearchService(FlightService flightService) {
    this.flightService = flightService;
  }

  public Flux<Flight> flights(FlightSearch query){
    return this.flightService.search(query)
        .filterWhen(flight -> this.flightService.availableSeats(flight.getId()).map(el -> el.getAvailable() > 0));
  }

}
