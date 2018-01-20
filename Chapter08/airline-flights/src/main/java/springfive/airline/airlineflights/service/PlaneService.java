package springfive.airline.airlineflights.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import springfive.airline.airlineflights.domain.Plane;

@Service
public class PlaneService {

    private final WebClient webClient;

    public PlaneService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Plane> plane(String id) {
        return this.webClient.mutate().baseUrl("").build().get().exchange().flatMap(clientResponse -> clientResponse.bodyToMono(Plane.class)
        );
    }

}
