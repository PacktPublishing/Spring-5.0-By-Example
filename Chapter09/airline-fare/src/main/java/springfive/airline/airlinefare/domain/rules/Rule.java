package springfive.airline.airlinefare.domain.rules;

import java.math.BigDecimal;

public interface Rule<T> {

  BigDecimal tax(T instance);

}
