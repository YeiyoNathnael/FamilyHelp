package nathnael.yeiyo.adu.ac.ae.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nathnael.yeiyo.adu.ac.ae.model.Family;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {
  List<Family> findByCommunityId(Long communityId);
}
