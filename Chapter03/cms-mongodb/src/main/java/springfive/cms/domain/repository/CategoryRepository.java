package springfive.cms.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import springfive.cms.domain.models.Category;

/**
 * @author claudioed on 29/10/17. Project cms
 */
public interface CategoryRepository extends ReactiveMongoRepository<Category,String> {
}
