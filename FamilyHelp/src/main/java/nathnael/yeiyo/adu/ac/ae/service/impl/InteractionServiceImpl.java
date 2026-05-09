package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.Interaction;
import nathnael.yeiyo.adu.ac.ae.repository.InteractionRepository;
import nathnael.yeiyo.adu.ac.ae.service.InteractionService;

@Service
public class InteractionServiceImpl implements InteractionService {

  @Autowired
  private InteractionRepository interactionRepository;

  @Override
  public Interaction save(Interaction interaction) {
    return interactionRepository.save(interaction);
  }

  @Override
  public Optional<Interaction> findById(Long id) {
    return interactionRepository.findById(id);
  }

  @Override
  public List<Interaction> findAll() {
    return interactionRepository.findAll();
  }

  @Override
  public Interaction update(Interaction interaction) {
    if (!interactionRepository.existsById(interaction.getId())) {
      throw new ResourceNotFoundException("Interaction not found with id: " + interaction.getId());
    }
    return interactionRepository.save(interaction);
  }

  @Override
  public void deleteById(Long id) {
    if (!interactionRepository.existsById(id)) {
      throw new ResourceNotFoundException("Interaction not found with id: " + id);
    }
    interactionRepository.deleteById(id);
  }

  @Override
  public List<Interaction> findByFamilyAId(Long familyId) {
    return interactionRepository.findByFamilyAId(familyId);
  }

  @Override
  public List<Interaction> findByFamilyBId(Long familyId) {
    return interactionRepository.findByFamilyBId(familyId);
  }

  @Override
  public Optional<Interaction> findByFamilyAIdAndFamilyBId(Long familyAId, Long familyBId) {
    return interactionRepository.findByFamilyAIdAndFamilyBId(familyAId, familyBId);
  }

}
