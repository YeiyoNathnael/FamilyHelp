package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.RaftLog;
import nathnael.yeiyo.adu.ac.ae.repository.RaftLogRepository;
import nathnael.yeiyo.adu.ac.ae.service.RaftLogService;

@Service
public class RaftLogServiceImpl implements RaftLogService {

  @Autowired
  private RaftLogRepository raftLogRepository;

  @Override
  public RaftLog save(RaftLog raftLog) {
    return raftLogRepository.save(raftLog);
  }

  @Override
  public Optional<RaftLog> findById(Long id) {
    return raftLogRepository.findById(id);
  }

  @Override
  public List<RaftLog> findAll() {
    return raftLogRepository.findAll();
  }

  @Override
  public RaftLog update(RaftLog raftLog) {
    if (!raftLogRepository.existsById(raftLog.getId())) {
      throw new ResourceNotFoundException("Raft log not found with id: " + raftLog.getId());
    }
    return raftLogRepository.save(raftLog);
  }

  @Override
  public void deleteById(Long id) {
    if (!raftLogRepository.existsById(id)) {
      throw new ResourceNotFoundException("Raft log not found with id: " + id);
    }
    raftLogRepository.deleteById(id);
  }

  @Override
  public Optional<RaftLog> findByEntryIndexAndTerm(Long entryIndex, Integer term) {
    return raftLogRepository.findByEntryIndexAndTerm(entryIndex, term);
  }

  @Override
  public List<RaftLog> findByStatus(String status) {
    return raftLogRepository.findByStatus(status);
  }

  @Override
  public List<RaftLog> findByNodeId(Long nodeId) {
    return raftLogRepository.findByNodeId(nodeId);
  }

  @Override
  public List<RaftLog> findByTerm(Integer term) {
    return raftLogRepository.findByTerm(term);
  }

}
