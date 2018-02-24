package springfive.airline.airlinefare.infra.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CredentialsProducer {

  private final String flightsClientId;

  private final String flightsClientSecret;

  private final String bookingsClientId;

  private final String bookingsClientSecret;

  public CredentialsProducer(@Value("${flights.oauth.client_id}") String flightsClientId,
                             @Value("${flights.oauth.client_secret}")String flightsClientSecret,
                             @Value("${bookings.oauth.client_id}")String bookingsClientId,
                             @Value("${bookings.oauth.client_secret}")String bookingsClientSecret) {
    this.flightsClientId = flightsClientId;
    this.flightsClientSecret = flightsClientSecret;
    this.bookingsClientId = bookingsClientId;
    this.bookingsClientSecret = bookingsClientSecret;
  }

  @Bean("flightsCredentials")
  public Credentials flights(){
    return Credentials.builder().clientId(this.flightsClientId).clientSecret(this.flightsClientSecret).build();
  }

  @Bean("bookingsCredentials")
  public Credentials bookings(){
    return Credentials.builder().clientId(this.bookingsClientId).clientSecret(this.bookingsClientSecret).build();
  }

}
