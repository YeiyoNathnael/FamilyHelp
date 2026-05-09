package nathnael.yeiyo.adu.ac.ae.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nathnael.yeiyo.adu.ac.ae.model.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
  List<Community> findByCity(String city);
}
