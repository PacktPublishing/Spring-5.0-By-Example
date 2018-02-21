package springfive.airline.airlineecommerce.domain.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.Class;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class RegularPrice {

  @JsonProperty("target_class")
  Class targetClass;

  BigDecimal price;

}
