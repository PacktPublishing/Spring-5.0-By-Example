package springfive.airline.authservice.service;

import org.springframework.stereotype.Service;
import springfive.airline.authservice.repository.CredentialRepository;

@Service
public class CredentialService {

  private final CredentialRepository credentialRepository;

  public CredentialService(CredentialRepository credentialRepository) {
    this.credentialRepository = credentialRepository;
  }


}
