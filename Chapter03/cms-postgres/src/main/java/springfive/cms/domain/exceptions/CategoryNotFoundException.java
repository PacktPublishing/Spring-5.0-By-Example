package springfive.cms.domain.exceptions;

import lombok.Getter;

/**
 * @author claudioed on 03/12/17. Project cms
 */
public class CategoryNotFoundException extends RuntimeException{

  @Getter
  private final String id;

  public CategoryNotFoundException(String id) {
    this.id = id;
  }

}
