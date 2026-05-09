package nathnael.yeiyo.adu.ac.ae.service;

import java.util.List;
import java.util.Optional;

import nathnael.yeiyo.adu.ac.ae.model.Interaction;

public interface InteractionService {
  Interaction save(Interaction interaction);
  Optional<Interaction> findById(Long id);
  List<Interaction> findAll();
  Interaction update(Interaction interaction);
  void deleteById(Long id);
  List<Interaction> findByFamilyAId(Long familyId);
  List<Interaction> findByFamilyBId(Long familyId);
  Optional<Interaction> findByFamilyAIdAndFamilyBId(Long familyAId, Long familyBId);
}
