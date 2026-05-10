package nathnael.yeiyo.adu.ac.ae.service;

import java.util.List;

import nathnael.yeiyo.adu.ac.ae.model.TrustScore;

public interface GossipService {
    void gossipRound();
    void receiveAndMerge(List<TrustScore> incoming);
}
