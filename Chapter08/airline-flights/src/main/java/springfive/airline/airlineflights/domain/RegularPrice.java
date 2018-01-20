package springfive.airline.airlineflights.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class RegularPrice {

  @JsonProperty("target_class")
  Class targetClass;

  BigDecimal price;

}
