package springfive.airline.authservice.infra.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthServer extends AuthorizationServerConfigurerAdapter {

  /**
   * One day in seconds for trusted apps
   */
  private static final int ONE_DAY = 86400;

  /**
   * Three hours in seconds
   */
  private static final int THREE_HOURS = 10800;

  private final AuthenticationManager authenticationManager;

  private final TokenStore tokenStore;

  private final JwtAccessTokenConverter jwtAccessTokenConverter;

  @Autowired
  public OAuth2AuthServer(AuthenticationManager authenticationManager, TokenStore tokenStore,
      JwtAccessTokenConverter jwtAccessTokenConverter) {
    this.authenticationManager = authenticationManager;
    this.tokenStore = tokenStore;
    this.jwtAccessTokenConverter = jwtAccessTokenConverter;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints.authenticationManager(this.authenticationManager).approvalStoreDisabled()
        .tokenStore(this.tokenStore).accessTokenConverter(this.jwtAccessTokenConverter);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
    oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients)throws Exception {
    clients
        .inMemory()
        .withClient("ecommerce") // ecommerce microservice
        .secret("9ecc8459ea5f39f9da55cb4d71a70b5d1e0f0b80")
        .authorizedGrantTypes("authorization_code", "refresh_token", "implicit",
            "client_credentials")
        .authorities("maintainer", "owner", "user")
        .scopes("read", "write")
        .accessTokenValiditySeconds(THREE_HOURS)
        .and()
        .withClient("442cf4015509eda9c03e5ca3aceef752") // planes microservice
        .secret("4f7ec648a48b9d3fa239b497f7b6b4d8019697bd")
        .authorizedGrantTypes("authorization_code", "refresh_token", "implicit",
            "client_credentials")
        .authorities("operator")
        .scopes("trust")
        .accessTokenValiditySeconds(ONE_DAY)
        .and()
        .withClient("917092f2d185310c0f4fa8ba3296e33d") // flights microservice
        .secret("f7641c289245d5d43d0aa96d0c14dbc6afba31fc")
        .authorizedGrantTypes("authorization_code", "refresh_token", "implicit",
            "client_credentials")
        .authorities("operator")
        .scopes("trust")
        .accessTokenValiditySeconds(ONE_DAY)
        .and()
        .withClient("65dfc684722921c89ae221d90f6ae2f9") // passengers microservice
        .secret("d658a112c1160fdcb5c6a4c3ce33c716cdb84160")
        .authorizedGrantTypes("authorization_code", "refresh_token", "implicit",
            "client_credentials")
        .authorities("operator")
        .scopes("trust")
        .accessTokenValiditySeconds(ONE_DAY)
        .and()
        .withClient("f8374efd71270dc152a2941bd0d9bed6") // bookings microservice
        .secret("da29f4b2ac608bdc8024b7ab084981fdf4014c16")
        .authorizedGrantTypes("authorization_code", "refresh_token", "implicit",
            "client_credentials")
        .authorities("operator")
        .scopes("trust")
        .accessTokenValiditySeconds(ONE_DAY)
        .and()
        .withClient("47d35bda81f57a8eb8f85a410e18001b") // fares service
        .secret("e186b815cfc1a9988030a21ad8f32d8a6559f3b8")
        .authorizedGrantTypes("authorization_code", "refresh_token", "implicit",
            "client_credentials")
        .authorities("operator")
        .scopes("trust")
        .accessTokenValiditySeconds(ONE_DAY);
  }

}