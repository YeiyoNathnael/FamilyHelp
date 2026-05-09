package nathnael.yeiyo.adu.ac.ae.service;

import java.util.List;

import nathnael.yeiyo.adu.ac.ae.model.UserRole;

public interface UserRoleService {

  UserRole save(UserRole userRole);

  List<UserRole> findByUserId(Long userId);

  void deleteByUserId(Long userId);
  
  List<UserRole> findAll();
}
