package springfive.cms.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import springfive.cms.domain.models.User;

/**
 * @author claudioed on 29/10/17. Project cms
 */
@Service
public interface UserRepository extends MongoRepository<User,String> {}
