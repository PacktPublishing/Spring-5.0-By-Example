package springfive.airline.airlinepassengers.domain.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlinepassengers.domain.Passenger;
import springfive.airline.airlinepassengers.domain.repository.PassengerRepository;
import springfive.airline.airlinepassengers.domain.resource.data.PassengerRequest;

@Service
public class PassengerService {

  private final PassengerRepository passengerRepository;

  public PassengerService(PassengerRepository passengerRepository) {
    this.passengerRepository = passengerRepository;
  }

  public Flux<Passenger> passengers(){
    return this.passengerRepository.findAll();
  }

  public Mono<Passenger> passenger(String id){
    return this.passengerRepository.findById(id);
  }

  public Mono<Passenger> create(@NonNull PassengerRequest passengerRequest){
    final Passenger passenger = Passenger.builder().address(passengerRequest.getAddress())
        .contact(passengerRequest.getContact()).bornDate(passengerRequest.getBornDate())
        .fidelityNumber(passengerRequest.getFidelityNumber())
        .lastName(passengerRequest.getLastName()).name(passengerRequest.getName())
        .documents(passengerRequest.getDocuments())
        .build();
    return this.passengerRepository.save(passenger);
  }

  public Mono<Void> delete(Passenger passenger){
    return this.passengerRepository.delete(passenger);
  }

}
