package springfive.airline.airlinefare.domain.flight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightInfo {

  private String id;

  private String number;

  private PlaneInfo plane;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PlaneInfo{

    private String id;

  }

}
