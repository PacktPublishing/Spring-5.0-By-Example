package springfive.airline.airlinepassengers.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfive.airline.airlinepassengers.service.PassengerService;

@RestController
@RequestMapping("/api/passengers")
public class PassengerResource {

    private final PassengerService passengerService;

    public PassengerResource(PassengerService passengerService) {
        this.passengerService = passengerService;
    }



}
