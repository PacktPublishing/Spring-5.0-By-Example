package springfive.cms.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import springfive.cms.domain.models.Category;

/**
 * @author claudioed on 29/10/17. Project cms
 */
public interface CategoryRepository extends JpaRepository<Category, String> {

  List<Category> findByName(String name);

  List<Category> findByNameIgnoreCaseStartingWith(String name);

}
