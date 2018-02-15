package springfive.cms.domain.vo;

import lombok.Data;
import springfive.cms.domain.models.Role;

/**
 * @author claudioed on 29/10/17. Project cms
 */
@Data
public class UserRequest {

  String identity;

  String name;

  Role role;

}
