package springfive.airline.airlineplanes.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfive.airline.airlineplanes.service.PlaneService;

@RestController
@RequestMapping("/api/plane")
public class PlaneResource {

  private final PlaneService planeService;

  public PlaneResource(PlaneService planeService) {
    this.planeService = planeService;
  }

}
