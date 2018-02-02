package springfive.airline.authservice.domain.data;

import lombok.Data;

@Data
public class CredentialRequest {

  String name;

  String email;

  String password;

}
