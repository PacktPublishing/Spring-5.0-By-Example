package springfive.airline.authservice.service;

import java.util.Objects;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import springfive.airline.authservice.domain.data.CredentialData;

@Component
public class AuthenticationService implements AuthenticationProvider {

  private final CredentialsDetailsService credentialUserDetailsService;

  public AuthenticationService(CredentialsDetailsService credentialUserDetailsService) {
    this.credentialUserDetailsService = credentialUserDetailsService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String nickname = authentication.getName();
    String password = (String) authentication.getCredentials();
    CredentialData credentialData = this.credentialUserDetailsService.loadUserByUsername(nickname);
    if (Objects.isNull(credentialData) || !credentialData.getEmail().equalsIgnoreCase(nickname)) {
      throw new BadCredentialsException("email not found or invalid.");
    }
    if (!password.equals(credentialData.getPassword())) {
      throw new BadCredentialsException("wrong password.");
    }
    return new UsernamePasswordAuthenticationToken(credentialData, password, credentialData.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return true;
  }

}