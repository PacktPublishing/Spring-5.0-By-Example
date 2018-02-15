package springfive.cms.domain.service;

import java.util.UUID;
import org.springframework.stereotype.Service;
import springfive.cms.domain.models.User;
import springfive.cms.domain.repository.UserRepository;
import springfive.cms.domain.vo.UserRequest;

/**
 * @author claudioed on 29/10/17. Project cms
 */
@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User update(String id,UserRequest userRequest){
    final User user = this.userRepository.findOne(id);
    user.setIdentity(userRequest.getIdentity());
    user.setName(userRequest.getName());
    user.setRole(userRequest.getRole());
    return this.userRepository.save(user);
  }

  public User create(UserRequest userRequest){
    User user = new User();
    user.setId(UUID.randomUUID().toString());
    user.setIdentity(userRequest.getIdentity());
    user.setName(userRequest.getName());
    user.setRole(userRequest.getRole());
    return this.userRepository.save(user);
  }

  public void delete(String id){
    final User user = this.userRepository.findOne(id);
    this.userRepository.delete(user);
  }

  public Iterable<User> findAll(){
    return this.userRepository.findAll();
  }

  public User findOne(String id){
    return this.userRepository.findOne(id);
  }

}
