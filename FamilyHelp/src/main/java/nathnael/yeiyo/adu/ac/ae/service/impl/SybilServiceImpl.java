package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nathnael.yeiyo.adu.ac.ae.model.Family;
import nathnael.yeiyo.adu.ac.ae.model.Feedback;
import nathnael.yeiyo.adu.ac.ae.model.Interaction;
import nathnael.yeiyo.adu.ac.ae.repository.FamilyRepository;
import nathnael.yeiyo.adu.ac.ae.repository.FeedbackRepository;
import nathnael.yeiyo.adu.ac.ae.repository.InteractionRepository;
import nathnael.yeiyo.adu.ac.ae.service.SybilService;

@Service
public class SybilServiceImpl implements SybilService {

    @Autowired private InteractionRepository interactionRepository;
    @Autowired private FamilyRepository familyRepository;
    @Autowired private FeedbackRepository feedbackRepository;

    private static final int WALKS = 500;
    private static final int STEPS = 10;
    private static final int MIN_COMPLETIONS_FOR_SEED = 30;
    private static final int MIN_DISTINCT_PARTNERS = 10;

    @Override
    public Map<Long, Double> runSybilScan() {

        // ── Step 1: find seed families ──
        List<Long> seeds = familyRepository.findAll().stream()
            .filter(f -> isTrustedSeed(f.getId()))
            .map(Family::getId)
            .collect(Collectors.toList());

        if (seeds.isEmpty()) return Collections.emptyMap();

        // ── Step 2: run random walks ──
        Map<Long, Integer> visitCounts = new HashMap<>();

        Random random = new Random();

        for (Long seed : seeds) {
            for (int walk = 0; walk < WALKS; walk++) {
                Long current = seed;

                for (int step = 0; step < STEPS; step++) {
                    // get all neighbors of current family
                    List<Interaction> edges = interactionRepository
                        .findByFamilyAIdOrFamilyBId(current, current);

                    if (edges.isEmpty()) break;

                    // weighted random selection
                    double totalWeight = edges.stream()
                        .mapToDouble(Interaction::getInteractionWeight).sum();

                    double rand = random.nextDouble() * totalWeight;
                    double cumulative = 0.0;

                    for (Interaction edge : edges) {
                        cumulative += edge.getInteractionWeight();
                        if (rand <= cumulative) {
                            // move to neighbor
                            current = edge.getFamilyAId() == current
                                ? edge.getFamilyBId()
                                : edge.getFamilyAId();
                            break;
                        }
                    }
                }

                // wherever we ended up, record visit
                visitCounts.merge(current, 1, Integer::sum);
            }
        }

        // ── Step 3: normalize to legitimacy scores 0-1 ──
        int maxVisits = visitCounts.values().stream()
            .mapToInt(Integer::intValue).max().orElse(1);

        Map<Long, Double> legitimacyScores = new HashMap<>();
        visitCounts.forEach((familyId, visits) ->
            legitimacyScores.put(familyId, (double) visits / maxVisits));

        // families with zero visits get legitimacy 0
        familyRepository.findAll().forEach(f -> {
            if (!legitimacyScores.containsKey(f.getId())) {
                legitimacyScores.put(f.getId(), 0.0);
            }
        });

        // ── Step 4: update legitimacy_weight on all feedback ──
        for (Feedback fb : feedbackRepository.findAll()) {
            double legitimacy = legitimacyScores
                .getOrDefault(fb.getReviewerFamilyId(), 0.0);
            fb.setLegitimacyWeight(legitimacy);
            feedbackRepository.save(fb);
        }

        return legitimacyScores;
    }

    private boolean isTrustedSeed(Long familyId) {
        List<Interaction> edges = interactionRepository
            .findByFamilyAIdOrFamilyBId(familyId, familyId);

        long distinctPartners = edges.stream()
            .map(e -> e.getFamilyAId() == familyId
                ? e.getFamilyBId() : e.getFamilyAId())
            .distinct().count();

        long totalCompleted = edges.stream()
            .mapToLong(e -> e.getInteractionCount()).sum();

        return totalCompleted >= MIN_COMPLETIONS_FOR_SEED
            && distinctPartners >= MIN_DISTINCT_PARTNERS;
    }
}