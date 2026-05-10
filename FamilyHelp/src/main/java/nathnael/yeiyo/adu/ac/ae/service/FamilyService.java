package nathnael.yeiyo.adu.ac.ae.service;

import java.util.List;
import java.util.Optional;

import nathnael.yeiyo.adu.ac.ae.model.Family;

public interface FamilyService {
  Family create(Family family);

  Family save(Family family);
  
  Optional<Family> findById(Long id);

  List<Family> findAll();

  Family update(Family family);

  void deleteById(Long id);
  
  List<Family> findByCommunityId(Long communityId);
}
