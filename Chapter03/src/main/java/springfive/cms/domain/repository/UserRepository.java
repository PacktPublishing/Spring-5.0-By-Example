package springfive.cms.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import springfive.cms.domain.models.User;

/**
 * @author claudioed on 29/10/17. Project cms
 */
public interface UserRepository extends JpaRepository<User,String> {}
