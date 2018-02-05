package springfive.airline.airlinefare.domain.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import springfive.airline.airlinefare.domain.Fare;

@Service
public class FareRepository {

  private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

  private final ObjectMapper mapper;

  public FareRepository(ReactiveRedisTemplate<String, String> reactiveRedisTemplate,
      ObjectMapper mapper) {
    this.reactiveRedisTemplate = reactiveRedisTemplate;
    this.mapper = mapper;
  }

  @SneakyThrows
  public Mono<Fare> create(Fare fare) {
    return this.reactiveRedisTemplate.opsForValue()
        .set("fare-" + fare.getId(), this.mapper.writeValueAsString(fare)).flatMap(el ->
            Mono.just(fare)
        );
  }

  public Mono<Fare> fare(String id) {
    return this.reactiveRedisTemplate.opsForValue().get("fare-" + id).flatMap(value -> {
      try {
        return Mono.just(this.mapper.readValue(value, Fare.class));
      } catch (IOException e) {
        throw new RuntimeException("Fail on connect redis");
      }
    });
  }

}
