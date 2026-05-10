package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nathnael.yeiyo.adu.ac.ae.model.Feedback;
import nathnael.yeiyo.adu.ac.ae.model.TaskTransaction;
import nathnael.yeiyo.adu.ac.ae.repository.FeedbackRepository;
import nathnael.yeiyo.adu.ac.ae.repository.TaskTransactionRepository;
import nathnael.yeiyo.adu.ac.ae.service.TrustService;

@Service
public class TrustServiceImpl implements TrustService {

    @Autowired private FeedbackRepository feedbackRepository;
    @Autowired private TaskTransactionRepository taskTransactionRepository;

    private static final double DECAY_HALF_LIFE_DAYS = 180.0;
    private static final double MATURITY_THRESHOLD = 15.0;
    private static final double BAYESIAN_PRIOR_WEIGHT = 5.0;
    private static final double CANCEL_PENALTY = 2.0;
    private static final double RATING_WEIGHT = 0.55;
    private static final double RELIABILITY_WEIGHT = 0.45;
    private static final double NEW_FAMILY_ANCHOR = 25.0;

    @Override
    public Map<String, Object> calculateTrustScore(Long familyId) {

        LocalDateTime now = LocalDateTime.now();

        // ── Step 1: get all completed and cancelled tasks ──
        List<TaskTransaction> allTasks = taskTransactionRepository.findAll();

        // time-weighted effective completions and cancellations
        double effectiveCompleted = 0.0;
        double effectiveCancelled = 0.0;

        for (TaskTransaction task : allTasks) {
            if (task.getCompletedAt() == null) {
                continue;
            }
            double daysSince = ChronoUnit.DAYS.between(task.getCompletedAt(), now);
            double w = Math.exp(-daysSince / DECAY_HALF_LIFE_DAYS);

            if ("COMPLETED".equals(task.getStatus())) effectiveCompleted += w;
            if ("CANCELLED".equals(task.getStatus())) effectiveCancelled += w;
        }

        // ── Step 2: volume confidence (single curve) ──
        double volumeConfidence = 1 - Math.exp(-effectiveCompleted / MATURITY_THRESHOLD);

        // ── Step 3: reliability factor ──
        double reliabilityFactor = effectiveCompleted /
            Math.max(effectiveCompleted + (effectiveCancelled * CANCEL_PENALTY), 1.0);

        // ── Step 4: rating factor with Bayesian smoothing + decay + legitimacy ──
        List<Feedback> feedbackList = feedbackRepository
            .findByReviewedFamilyId(familyId);

        double weightedRatingSum = 0.0;
        double weightSum = 0.0;

        for (Feedback fb : feedbackList) {
            if (fb.getCreatedAt() == null) {
                continue;
            }
            double daysSince = ChronoUnit.DAYS.between(fb.getCreatedAt(), now);
            double w = Math.exp(-daysSince / DECAY_HALF_LIFE_DAYS);
            Double legitimacyValue = fb.getLegitimacyWeight();
            Double legitimacy = legitimacyValue == null ? 0.5 : legitimacyValue;

            weightedRatingSum += fb.getNumericalRating() * w * legitimacy;
            weightSum += w * legitimacy;
        }

        // Bayesian: anchor toward 3.0 with prior weight of 5
        double adjustedRating = (3.0 * BAYESIAN_PRIOR_WEIGHT + weightedRatingSum)
                              / (BAYESIAN_PRIOR_WEIGHT + weightSum);
        double ratingFactor = (adjustedRating - 1.0) / 4.0;

        // ── Step 5: quality score ──
        double qualityScore = (ratingFactor * RATING_WEIGHT)
                            + (reliabilityFactor * RELIABILITY_WEIGHT);

        // ── Step 6: final score ──
        double finalScore = NEW_FAMILY_ANCHOR
            + (qualityScore * (100.0 - NEW_FAMILY_ANCHOR) * volumeConfidence);

        // ── Step 7: CRDT — update this node's contribution slot ──
        // (contribution stored per node, summed across all nodes for display)
        // Here we store the contribution this node computed
        // Other nodes' contributions come via gossip

        // ── Step 8: derive tier ──
        String tier;
        if (finalScore < 40) tier = "SEEDLING";
        else if (finalScore < 58) tier = "ROOTED";
        else if (finalScore < 74) tier = "BRANCHED";
        else if (finalScore < 88) tier = "TRUSTED";
        else tier = "ELDER";

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("familyId", familyId);
        result.put("derivedScore", Math.round(finalScore));
        result.put("tier", tier);
        result.put("effectiveCompletions", Math.round(effectiveCompleted * 10.0) / 10.0);
        result.put("reliabilityFactor", Math.round(reliabilityFactor * 100.0) / 100.0);
        result.put("adjustedRating", Math.round(adjustedRating * 10.0) / 10.0);
        result.put("volumeConfidence", Math.round(volumeConfidence * 100.0) / 100.0);

        return result;
    }
}