package nathnael.yeiyo.adu.ac.ae.service;

import java.util.List;
import java.util.Optional;

import nathnael.yeiyo.adu.ac.ae.model.Community;

public interface CommunityService {

  Community save(Community community);

  Optional<Community> findById(Long id);

  List<Community> findAll();

  Community update(Community community);

  void deleteById(Long id);
  
  List<Community> findByCity(String city);
}
