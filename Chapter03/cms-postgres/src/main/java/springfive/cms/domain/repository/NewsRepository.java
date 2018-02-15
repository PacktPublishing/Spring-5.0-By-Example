package springfive.cms.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springfive.cms.domain.models.News;

public interface NewsRepository extends JpaRepository<News, String> {

}