package springfive.airline.airlinefare.infra.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class RedisConfiguration {

  @Bean
  public ReactiveRedisTemplate<String,String> reactiveRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory){
    return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, RedisSerializationContext.string());
  }

  @Bean
  public ObjectMapper mapper(){
    return new ObjectMapper();
  }

}
