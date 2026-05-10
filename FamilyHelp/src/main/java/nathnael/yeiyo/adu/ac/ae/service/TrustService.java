package nathnael.yeiyo.adu.ac.ae.service;

import java.util.Map;

public interface TrustService {
    Map<String, Object> calculateTrustScore(Long familyId);
}
