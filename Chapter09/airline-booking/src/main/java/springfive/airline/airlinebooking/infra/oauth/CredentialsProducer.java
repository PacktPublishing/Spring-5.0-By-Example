package springfive.airline.airlinebooking.infra.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CredentialsProducer {

  private final String faresClientId;

  private final String faresClientSecret;

  private final String planesClientId;

  private final String planesClientSecret;

  private final String flightsClientId;

  private final String flightsClientSecret;

  public CredentialsProducer(
      @Value("${fares.oauth.client_id}") String faresClientId,
      @Value("${fares.oauth.client_secret}") String faresClientSecret,
      @Value("${planes.oauth.client_id}") String planesClientId,
      @Value("${planes.oauth.client_secret}") String planesClientSecret,
      @Value("${flights.oauth.client_id}")String flightsClientId,
      @Value("${flights.oauth.client_secret}")String flightsClientSecret) {
    this.faresClientId = faresClientId;
    this.faresClientSecret = faresClientSecret;
    this.planesClientId = planesClientId;
    this.planesClientSecret = planesClientSecret;
    this.flightsClientId = flightsClientId;
    this.flightsClientSecret = flightsClientSecret;
  }

  @Bean("faresCredentials")
  public Credentials fares(){
    return Credentials.builder().clientId(this.faresClientId).clientSecret(this.faresClientSecret).build();
  }

  @Bean("planesCredentials")
  public Credentials planes(){
    return Credentials.builder().clientId(this.planesClientId).clientSecret(this.planesClientSecret).build();
  }

  @Bean("flightsCredentials")
  public Credentials flights(){
    return Credentials.builder().clientId(this.flightsClientId).clientSecret(this.flightsClientSecret).build();
  }

}
