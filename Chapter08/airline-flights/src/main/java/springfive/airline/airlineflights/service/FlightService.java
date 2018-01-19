package springfive.airline.airlineflights.service;

import org.springframework.stereotype.Service;
import springfive.airline.airlineflights.repository.FlightRepository;

@Service
public class FlightService {

  private final FlightRepository flightRepository;

  public FlightService(FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
  }

}
