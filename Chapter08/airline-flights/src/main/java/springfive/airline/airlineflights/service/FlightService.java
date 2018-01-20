package springfive.airline.airlineflights.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlineflights.domain.Flight;
import springfive.airline.airlineflights.repository.FlightRepository;
import springfive.airline.airlineflights.resource.data.FlightRequest;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    private final PlaneService planeService;

    public FlightService(FlightRepository flightRepository, PlaneService planeService) {
        this.flightRepository = flightRepository;
        this.planeService = planeService;
    }

    public Mono<Flight> flight(String id) {
        return this.flightRepository.findById(id);
    }

    public Flux<Flight> flights() {
        return this.flightRepository.findAll();
    }

    public Mono<Flight> save(@NonNull FlightRequest flight) {
        return this.planeService.plane(flight.getPlaneId()).flatMap(plane -> this.flightRepository.save(Flight.builder()
            .from(flight.getFrom())
            .to(flight.getTo())
            .plane(plane)
            .arriveAt(flight.getArriveAt())
            .departureAt(flight.getDepartureAt())
            .connections(flight.getConnections())
            .build()));
    }

    public Mono<Void> deleteFlight(@NonNull Flight flight){
        return this.flightRepository.delete(flight);
    }

    public Mono<Flight> update(String id,FlightRequest flightRequest){
        return this.flightRepository.findById(id).flatMap(data -> this.flightRepository.save(data.fromFlightRequest(flightRequest)));
    }

}
