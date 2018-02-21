package springfive.airline.airlineecommerce.domain.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import springfive.airline.airlineecommerce.domain.FlightSearch;
import springfive.airline.airlineecommerce.domain.flight.Flight;

@Service
public class FlightSearchService {

  private final BookingService bookingService;

  private final FlightService flightService;

  public FlightSearchService(BookingService bookingService, FlightService flightService) {
    this.bookingService = bookingService;
    this.flightService = flightService;
  }

  public Flux<Flight> flights(FlightSearch query){
    return this.flightService.search(query)
        .filterWhen(flight -> this.bookingService.totalBooked(flight.getId()).map(el -> el.getTotal() > 0));
  }

}
