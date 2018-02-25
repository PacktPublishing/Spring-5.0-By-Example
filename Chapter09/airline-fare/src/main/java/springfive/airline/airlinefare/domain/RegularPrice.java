package springfive.airline.airlinefare.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class RegularPrice {

  @JsonProperty("target_class")
  public
  Class targetClass;

  BigDecimal price;

}
