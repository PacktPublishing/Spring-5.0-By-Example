package springfive.airline.airlinepassengers.service;

import org.springframework.stereotype.Service;
import springfive.airline.airlinepassengers.repository.PassengerRepository;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }


}
