package springfive.airline.authservice.service;

import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springfive.airline.authservice.domain.Credential;
import springfive.airline.authservice.domain.data.CredentialRequest;
import springfive.airline.authservice.repository.CredentialRepository;

@Service
public class CredentialService {

  private final CredentialRepository credentialRepository;

  private final PasswordEncoder passwordEncoder;

  public CredentialService(CredentialRepository credentialRepository,
      PasswordEncoder passwordEncoder) {
    this.credentialRepository = credentialRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public CredentialRequest newCredential(@NonNull CredentialRequest credentialRequest){
    final Credential credential = Credential.builder().email(credentialRequest.getEmail())
        .name(credentialRequest.getName()).password(this.passwordEncoder.encode(credentialRequest.getPassword()))
        .scope("operator").build();
    this.credentialRepository.save(credential);
    return credentialRequest;
  }

}
