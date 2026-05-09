package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.User;
import nathnael.yeiyo.adu.ac.ae.repository.UserRepository;
import nathnael.yeiyo.adu.ac.ae.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  @Override
  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User update(User user) {
    if (!userRepository.existsById(user.getId())) {
      throw new ResourceNotFoundException("User not found with id: " + user.getId());
    }
    return userRepository.save(user);
  }

  @Override
  public void deleteById(Long id) {
    if (!userRepository.existsById(id)) {
      throw new ResourceNotFoundException("User not found with id: " + id);
    }
    userRepository.deleteById(id);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public List<User> findByFamilyId(Long familyId) {
    return userRepository.findByFamilyId(familyId);
  }

}
