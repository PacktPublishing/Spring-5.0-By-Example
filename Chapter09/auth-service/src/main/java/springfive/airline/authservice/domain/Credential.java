package springfive.airline.authservice.domain;

import static java.util.stream.Collectors.toSet;

import java.util.Set;
import java.util.stream.Stream;
import lombok.Builder;
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

  @Builder
  public static Credential newCredential(String name,String email,String password,String scope){
    Credential newCredential = new Credential();
    newCredential.email = email;
    newCredential.name = name;
    newCredential.password = password;
    newCredential.scopes = Stream.of(Scope.builder().value(scope).build()).collect(toSet());
    return newCredential;
  }

}
