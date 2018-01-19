package springfive.airline.airlineflights.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfive.airline.airlineflights.service.FlightService;

@RestController
@RequestMapping("/api/flights")
public class FlightResource {

  private final FlightService flightService;

  public FlightResource(FlightService flightService) {
    this.flightService = flightService;
  }

}
