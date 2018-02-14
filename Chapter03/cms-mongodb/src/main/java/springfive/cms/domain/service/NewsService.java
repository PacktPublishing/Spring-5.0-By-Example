package springfive.cms.domain.service;

import org.springframework.stereotype.Service;
import springfive.cms.domain.repository.NewsRepository;

/**
 * @author claudioed on 29/10/17. Project cms
 */
@Service
public class NewsService {

  private final NewsRepository newsRepository;

  public NewsService(NewsRepository newsRepository) {
    this.newsRepository = newsRepository;
  }

}
