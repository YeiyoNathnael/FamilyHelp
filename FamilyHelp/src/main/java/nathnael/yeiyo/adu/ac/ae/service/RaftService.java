package nathnael.yeiyo.adu.ac.ae.service;

import nathnael.yeiyo.adu.ac.ae.model.RaftLog;

public interface RaftService {
    RaftLog proposeAcceptance(Long applicationId);
    boolean appendEntry(RaftLog entry);
    boolean requestVote(Long candidateId, int term, long lastLogIndex);
    void startElection();
}
