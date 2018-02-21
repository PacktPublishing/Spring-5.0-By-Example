package springfive.airline.airlineecommerce.infra.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuthTokenConfiguration {

  @Value("${config.oauth2.privateKey}")
  private String privateKey;

  @Value("${config.oauth2.publicKey}")
  private String publicKey;

  @Bean
  public JwtTokenStore tokenStore() throws Exception {
    JwtAccessTokenConverter enhancer = new JwtAccessTokenConverter();
    enhancer.setSigningKey(privateKey);
    enhancer.setVerifierKey(publicKey);
    enhancer.afterPropertiesSet();
    return new JwtTokenStore(enhancer);
  }

}
