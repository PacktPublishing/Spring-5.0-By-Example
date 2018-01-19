package springfive.airline.airlineplanes.service;

import org.springframework.stereotype.Service;
import springfive.airline.airlineplanes.repository.PlaneRepository;

@Service
public class PlaneService {

  private final PlaneRepository planeRepository;

  public PlaneService(PlaneRepository planeRepository) {
    this.planeRepository = planeRepository;
  }

}
