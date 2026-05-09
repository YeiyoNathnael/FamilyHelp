package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.TrustScore;
import nathnael.yeiyo.adu.ac.ae.repository.TrustScoreRepository;
import nathnael.yeiyo.adu.ac.ae.service.TrustScoreService;

@Service
public class TrustScoreServiceImpl implements TrustScoreService {

  @Autowired
  private TrustScoreRepository trustScoreRepository;

  @Override
  public TrustScore save(TrustScore trustScore) {
    return trustScoreRepository.save(trustScore);
  }

  @Override
  public Optional<TrustScore> findById(Long id) {
    return trustScoreRepository.findById(id);
  }

  @Override
  public List<TrustScore> findAll() {
    return trustScoreRepository.findAll();
  }

  @Override
  public TrustScore update(TrustScore trustScore) {
    if (!trustScoreRepository.existsById(trustScore.getId())) {
      throw new ResourceNotFoundException("Trust score not found with id: " + trustScore.getId());
    }
    return trustScoreRepository.save(trustScore);
  }

  @Override
  public void deleteById(Long id) {
    if (!trustScoreRepository.existsById(id)) {
      throw new ResourceNotFoundException("Trust score not found with id: " + id);
    }
    trustScoreRepository.deleteById(id);
  }

  @Override
  public List<TrustScore> findByFamilyId(Long familyId) {
    return trustScoreRepository.findByFamilyId(familyId);
  }

  @Override
  public List<TrustScore> findByNodeId(Long nodeId) {
    return trustScoreRepository.findByNodeId(nodeId);
  }

  @Override
  public Optional<TrustScore> findByFamilyIdAndNodeId(Long familyId, Long nodeId) {
    return trustScoreRepository.findByFamilyIdAndNodeId(familyId, nodeId);
  }

}
