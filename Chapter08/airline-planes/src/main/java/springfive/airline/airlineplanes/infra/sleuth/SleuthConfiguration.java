package springfive.airline.airlineplanes.infra.sleuth;

import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SleuthConfiguration {

  @Bean
  public Sampler defaultSampler() {
    return new AlwaysSampler();
  }

}
