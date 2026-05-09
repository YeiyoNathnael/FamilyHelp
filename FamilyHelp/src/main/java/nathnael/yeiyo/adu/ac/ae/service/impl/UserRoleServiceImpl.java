package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.UserRole;
import nathnael.yeiyo.adu.ac.ae.repository.UserRoleRepository;
import nathnael.yeiyo.adu.ac.ae.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {

  @Autowired
  private UserRoleRepository userRoleRepository;

  @Override
  public UserRole save(UserRole userRole) {
    return userRoleRepository.save(userRole);
  }

  @Override
  public List<UserRole> findByUserId(Long userId) {
    return userRoleRepository.findByUserId(userId);
  }

  @Override
  public void deleteByUserId(Long userId) {
    if (userRoleRepository.findByUserId(userId).isEmpty()) {
      throw new ResourceNotFoundException("User role not found for user id: " + userId);
    }
    userRoleRepository.deleteByUserId(userId);
  }

  @Override
  public List<UserRole> findAll() {
    return userRoleRepository.findAll();
  }

}
