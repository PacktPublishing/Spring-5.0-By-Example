package springfive.airline.airlinefare.domain.flight;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import lombok.Data;
import springfive.airline.airlinefare.domain.Plane;
import springfive.airline.airlinefare.domain.RegularPrice;

@Data
public class Flight {

  String id;

  String number;

  LocalDateTime departureAt;

  LocalDateTime arriveAt;

  Set<RegularPrice> prices;

  Plane plane;

  public Optional<BigDecimal> categoryPrice(String classId){
    return prices.stream().filter(price-> price.targetClass.getId().equalsIgnoreCase(classId))
        .map(RegularPrice::getPrice).findFirst();
  }

}
