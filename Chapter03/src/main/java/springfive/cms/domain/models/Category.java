package springfive.cms.domain.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author claudioed on 28/10/17. Project cms
 */
@Data
@Document(collection = "categories")
public class Category {

  @Id
  String id;

  String name;

}
