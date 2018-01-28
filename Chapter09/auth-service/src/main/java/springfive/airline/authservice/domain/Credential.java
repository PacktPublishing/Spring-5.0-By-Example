package springfive.airline.authservice.domain;

import java.util.Set;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "credentials")
public class Credential {

  @Id
  String id;

  String name;

  String email;

  String password;

  Set<Scope> scopes;

}
