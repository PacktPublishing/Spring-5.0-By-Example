package springfive.airline.authservice.resource;

import java.security.Principal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credential")
public class CredentialResource {

  @RequestMapping(method = RequestMethod.GET)
  public Principal user(Principal user) {
    return user;
  }

}
