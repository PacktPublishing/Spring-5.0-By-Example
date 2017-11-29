package springfive.cms.domain.models;

import lombok.Data;

/**
 * @author claudioed on 28/10/17. Project cms
 */
@Data
public class User {

  String id;

  String identity;

  String name;

  Role role;

}
