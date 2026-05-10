package nathnael.yeiyo.adu.ac.ae.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nathnael.yeiyo.adu.ac.ae.model.RaftLog;

@Repository
public interface RaftLogRepository extends JpaRepository<RaftLog, Long> {
  Optional<RaftLog> findByEntryIndexAndTerm(Long entryIndex, Integer term);
  List<RaftLog> findByStatus(String status);
  List<RaftLog> findByNodeId(Long nodeId);
  List<RaftLog> findByTerm(Integer term);
  Optional<RaftLog> findTopByOrderByEntryIndexDesc();
}
