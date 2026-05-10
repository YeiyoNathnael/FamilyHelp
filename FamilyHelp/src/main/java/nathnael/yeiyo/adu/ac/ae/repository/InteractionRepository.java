package nathnael.yeiyo.adu.ac.ae.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nathnael.yeiyo.adu.ac.ae.model.Interaction;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {
  List<Interaction> findByFamilyAId(Long familyId);
  List<Interaction> findByFamilyBId(Long familyId);
  Optional<Interaction> findByFamilyAIdAndFamilyBId(Long familyAId, Long familyBId);
  List<Interaction> findByFamilyAIdOrFamilyBId(Long familyAId, Long familyBId);
}
