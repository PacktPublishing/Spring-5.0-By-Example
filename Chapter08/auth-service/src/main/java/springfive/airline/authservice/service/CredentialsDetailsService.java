package springfive.airline.authservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import springfive.airline.authservice.domain.Credential;
import springfive.airline.authservice.domain.data.CredentialData;
import springfive.airline.authservice.repository.CredentialRepository;

@Component
public class CredentialsDetailsService implements UserDetailsService {

  private final CredentialRepository credentialRepository;

  public CredentialsDetailsService(CredentialRepository credentialRepository) {
    this.credentialRepository = credentialRepository;
  }

  @Override
  public CredentialData loadUserByUsername(String email) throws UsernameNotFoundException {
    final Credential credential = this.credentialRepository.findByEmail(email);
    return CredentialData.builder().email(credential.getEmail()).password(credential.getPassword()).scopes(credential.getScopes()).build();
  }

}
