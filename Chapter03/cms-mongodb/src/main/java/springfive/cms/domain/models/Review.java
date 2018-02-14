package springfive.cms.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author claudioed on 29/10/17. Project cms
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

  String userId;

  String status;

}
