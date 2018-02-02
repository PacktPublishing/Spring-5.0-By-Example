package springfive.airline.authservice.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfive.airline.authservice.domain.data.CredentialRequest;
import springfive.airline.authservice.service.CredentialService;

@RestController
@RequestMapping("/register")
public class RegisterResource {

  private final CredentialService credentialService;

  public RegisterResource(CredentialService credentialService) {
    this.credentialService = credentialService;
  }

  @PostMapping
  public ResponseEntity<CredentialRequest> newCredential(@RequestBody CredentialRequest credentialRequest){
    return ResponseEntity.ok(this.credentialService.newCredential(credentialRequest));
  }

}
