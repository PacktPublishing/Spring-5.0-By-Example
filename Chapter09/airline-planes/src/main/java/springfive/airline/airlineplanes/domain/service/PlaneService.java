package springfive.airline.airlineplanes.domain.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlineplanes.domain.Plane;
import springfive.airline.airlineplanes.domain.repository.PlaneRepository;
import springfive.airline.airlineplanes.domain.resource.data.PlaneRequest;

@Service
public class PlaneService {

  private final PlaneRepository planeRepository;

  public PlaneService(PlaneRepository planeRepository) {
    this.planeRepository = planeRepository;
  }

  public Flux<Plane> planes(){
    return this.planeRepository.findAll();
  }

  public Mono<Plane> plane(@NonNull String id){
    return this.planeRepository.findById(id);
  }

  public Mono<Void> deletePlane(@NonNull Plane plane){
    return this.planeRepository.delete(plane);
  }

  public Mono<Plane> create(@NonNull PlaneRequest planeRequest){
    final Plane plane = Plane.builder().owner(planeRequest.getOwner())
        .planeModel(planeRequest.getModel()).seats(planeRequest.getSeats())
        .notes(planeRequest.getNotes()).build();
    return this.planeRepository.save(plane);
  }

  public Mono<Plane> update(@NonNull String id,@NonNull PlaneRequest planeRequest){
    return this.planeRepository.findById(id)
        .flatMap(plane -> Mono.just(plane.fromPlaneRequest(planeRequest)))
        .flatMap(this.planeRepository::save);
  }

}
