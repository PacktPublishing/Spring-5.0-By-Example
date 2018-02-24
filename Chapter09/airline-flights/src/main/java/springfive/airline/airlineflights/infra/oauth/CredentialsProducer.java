package springfive.airline.airlineflights.infra.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CredentialsProducer {

  private final String planesClientId;

  private final String planesClientSecret;

  private final String bookingsClientId;

  private final String bookingsClientSecret;

  public CredentialsProducer(@Value("${planes.oauth.client_id}") String planesClientId,
                             @Value("${planes.oauth.client_secret}")String planesClientSecret,
                             @Value("${bookings.oauth.client_id}")String bookingsClientId,
                             @Value("${bookings.oauth.client_secret}")String bookingsClientSecret) {
    this.planesClientId = planesClientId;
    this.planesClientSecret = planesClientSecret;
    this.bookingsClientId = bookingsClientId;
    this.bookingsClientSecret = bookingsClientSecret;
  }


  @Bean("planesCredentials")
  public Credentials flights(){
    return Credentials.builder().clientId(this.planesClientId).clientSecret(this.planesClientSecret).build();
  }

  @Bean("bookingsCredentials")
  public Credentials bookings(){
    return Credentials.builder().clientId(this.bookingsClientId).clientSecret(this.bookingsClientSecret).build();
  }

}
