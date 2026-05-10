package nathnael.yeiyo.adu.ac.ae.service;

import java.util.List;
import java.util.Optional;

import nathnael.yeiyo.adu.ac.ae.model.User;

public interface UserService {

  User create(User user);

  User save(User user);

  Optional<User> findById(Long id);

  List<User> findAll();

  User update(User user);

  void deleteById(Long id);

  Optional<User> findByEmail(String email);
  
  List<User> findByFamilyId(Long familyId);
}
