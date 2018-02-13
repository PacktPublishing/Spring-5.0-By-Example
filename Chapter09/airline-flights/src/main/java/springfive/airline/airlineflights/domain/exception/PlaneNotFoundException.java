package springfive.airline.airlineflights.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaneNotFoundException extends RuntimeException {

  String id;

}
