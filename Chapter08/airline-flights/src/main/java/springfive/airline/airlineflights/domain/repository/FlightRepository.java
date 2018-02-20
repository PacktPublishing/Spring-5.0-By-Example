package springfive.airline.airlineflights.domain.repository;

import java.time.LocalDateTime;
import org.apache.tomcat.jni.Local;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import springfive.airline.airlineflights.domain.Flight;

public interface FlightRepository extends ReactiveCrudRepository<Flight,String>{

  Flux<Flight> findByFromCodeAndToCodeAndDepartureAtAfterAndAndArriveAtBefore(String fromCode,String toCode,LocalDateTime departure,LocalDateTime arriveAt);

}
