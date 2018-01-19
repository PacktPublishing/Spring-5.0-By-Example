package springfive.airline.airlineflights.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import springfive.airline.airlineflights.domain.Flight;

public interface FlightRepository extends ReactiveCrudRepository<Flight,String>{
}
