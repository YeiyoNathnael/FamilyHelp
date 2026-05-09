package nathnael.yeiyo.adu.ac.ae.service;

import java.util.List;
import java.util.Optional;

import nathnael.yeiyo.adu.ac.ae.model.RaftLog;

public interface RaftLogService {

  RaftLog save(RaftLog raftLog);

  Optional<RaftLog> findById(Long id);

  List<RaftLog> findAll();

  RaftLog update(RaftLog raftLog);

  void deleteById(Long id);

  Optional<RaftLog> findByEntryIndexAndTerm(Long entryIndex, Integer term);

  List<RaftLog> findByStatus(String status);

  List<RaftLog> findByNodeId(Long nodeId);
  
  List<RaftLog> findByTerm(Integer term);
}
