package springfive.airline.airlinebooking.domain;

import lombok.Data;

@Data
public class Seat {

  String identity;

  Integer row;

  Class category;

  public String categoryId(){
    return this.category.getId();
  }

}
