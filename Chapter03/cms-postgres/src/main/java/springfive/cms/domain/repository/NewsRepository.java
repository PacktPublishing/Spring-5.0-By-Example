package springfive.cms.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springfive.cms.domain.models.News;

/**
 * @author claudioed on 14/11/17. Project cms
 */
public interface NewsRepository extends JpaRepository<News,String> {
}