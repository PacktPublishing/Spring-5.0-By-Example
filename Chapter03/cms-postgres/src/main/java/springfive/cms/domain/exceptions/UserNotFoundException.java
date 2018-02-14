package springfive.cms.domain.exceptions;

import lombok.Getter;

/**
 * @author claudioed on 03/12/17. Project cms
 */
public class UserNotFoundException extends RuntimeException{

  @Getter
  private final String id;

  public UserNotFoundException(String id) {
    this.id = id;
  }

}
