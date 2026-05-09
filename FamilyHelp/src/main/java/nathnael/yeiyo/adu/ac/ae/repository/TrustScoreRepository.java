package nathnael.yeiyo.adu.ac.ae.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nathnael.yeiyo.adu.ac.ae.model.TrustScore;

@Repository
public interface TrustScoreRepository extends JpaRepository<TrustScore, Long> {
  List<TrustScore> findByFamilyId(Long familyId);
  List<TrustScore> findByNodeId(Long nodeId);
  Optional<TrustScore> findByFamilyIdAndNodeId(Long familyId, Long nodeId);
}
