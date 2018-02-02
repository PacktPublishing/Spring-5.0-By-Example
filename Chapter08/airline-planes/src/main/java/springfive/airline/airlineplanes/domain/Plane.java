package springfive.airline.airlineplanes.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import springfive.airline.airlineplanes.domain.resource.data.PlaneRequest;

@Data
@Document(collection = "planes")
@JsonInclude(Include.NON_NULL)
public class Plane {

  @Id
  String id;

  String owner;

  PlaneModel model;

  Set<Seat> seats;

  String notes;

  @Builder
  public static Plane newPlane(String owner,PlaneModel planeModel,Set<Seat> seats,String notes){
    Plane plane = new Plane();
    plane.owner = owner;
    plane.model = planeModel;
    plane.seats = seats;
    plane.notes = notes;
    return plane;
  }

  public Plane fromPlaneRequest(@NonNull PlaneRequest planeRequest){
    this.owner = planeRequest.getOwner();
    this.model = planeRequest.getModel();
    this.seats = planeRequest.getSeats();
    this.notes = planeRequest.getNotes();
    return this;
  }

}
