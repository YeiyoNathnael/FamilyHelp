package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.Family;
import nathnael.yeiyo.adu.ac.ae.repository.FamilyRepository;
import nathnael.yeiyo.adu.ac.ae.service.FamilyService;

@Service
public class FamilyServiceImpl implements FamilyService {

  @Autowired
  private FamilyRepository familyRepository;

  @Override
  public Family create(Family family) {
    return familyRepository.save(family);
  }

  @Override
  public Family save(Family family) {
    return familyRepository.save(family);
  }

  @Override
  public Optional<Family> findById(Long id) {
    return familyRepository.findById(id);
  }

  @Override
  public List<Family> findAll() {
    return familyRepository.findAll();
  }

  @Override
  public Family update(Family family) {
    if (!familyRepository.existsById(family.getId())) {
      throw new ResourceNotFoundException("Family not found with id: " + family.getId());
    }
    return familyRepository.save(family);
  }

  @Override
  public void deleteById(Long id) {
    if (!familyRepository.existsById(id)) {
      throw new ResourceNotFoundException("Family not found with id: " + id);
    }
    familyRepository.deleteById(id);
  }

  @Override
  public List<Family> findByCommunityId(Long communityId) {
    return familyRepository.findByCommunityId(communityId);
  }

}
