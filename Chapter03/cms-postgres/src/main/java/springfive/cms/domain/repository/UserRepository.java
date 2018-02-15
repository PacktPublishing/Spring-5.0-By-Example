package springfive.cms.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import springfive.cms.domain.models.User;

public interface UserRepository extends JpaRepository<User, String> {

}
