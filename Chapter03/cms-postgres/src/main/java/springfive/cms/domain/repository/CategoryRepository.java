package springfive.cms.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import springfive.cms.domain.models.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {

  List<Category> findByName(String name);

  List<Category> findByNameIgnoreCaseStartingWith(String name);

}
