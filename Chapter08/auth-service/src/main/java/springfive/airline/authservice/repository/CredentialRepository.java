package springfive.airline.authservice.repository;

import org.springframework.data.repository.CrudRepository;
import springfive.airline.authservice.domain.Credential;

public interface CredentialRepository extends CrudRepository<Credential,String> {

  Credential findByEmail(String email);

}
