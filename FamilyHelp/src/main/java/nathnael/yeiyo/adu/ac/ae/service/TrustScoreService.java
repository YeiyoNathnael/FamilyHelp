package nathnael.yeiyo.adu.ac.ae.service;

import java.util.List;
import java.util.Optional;

import nathnael.yeiyo.adu.ac.ae.model.TrustScore;

public interface TrustScoreService {

  TrustScore save(TrustScore trustScore);

  Optional<TrustScore> findById(Long id);

  List<TrustScore> findAll();

  TrustScore update(TrustScore trustScore);

  void deleteById(Long id);

  List<TrustScore> findByFamilyId(Long familyId);

  List<TrustScore> findByNodeId(Long nodeId);
  
  Optional<TrustScore> findByFamilyIdAndNodeId(Long familyId, Long nodeId);
}
