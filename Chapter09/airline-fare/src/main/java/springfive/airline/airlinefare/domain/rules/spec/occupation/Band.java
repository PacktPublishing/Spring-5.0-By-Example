package springfive.airline.airlinefare.domain.rules.spec.occupation;

import lombok.Value;

@Value
public class Band {

  Double start;

  Double end;

  public Boolean inside(Double value){
    return value >= start && value <= end;
  }

}

