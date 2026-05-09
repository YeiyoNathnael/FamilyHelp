package nathnael.yeiyo.adu.ac.ae.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.Family;
import nathnael.yeiyo.adu.ac.ae.model.Interaction;
import nathnael.yeiyo.adu.ac.ae.model.TrustScore;
import nathnael.yeiyo.adu.ac.ae.model.User;
import nathnael.yeiyo.adu.ac.ae.service.FamilyService;
import nathnael.yeiyo.adu.ac.ae.service.InteractionService;
import nathnael.yeiyo.adu.ac.ae.service.TrustScoreService;
import nathnael.yeiyo.adu.ac.ae.service.UserService;

@RestController
@RequestMapping("/families")
public class FamilyController {

	@Autowired
	private FamilyService familyService;

	@Autowired
	private UserService userService;

	@Autowired
	private TrustScoreService trustScoreService;

	@Autowired
	private InteractionService interactionService;

	@GetMapping("/{id}")
	public Family getFamilyById(@PathVariable Long id) {
		return familyService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Family not found with id: " + id));
	}

	@GetMapping("/{id}/members")
	public List<User> getMembers(@PathVariable Long id) {
		return userService.findByFamilyId(id);
	}

	@PutMapping("/{id}")
	public Family updateFamily(@PathVariable Long id, @RequestBody Family family) {
		family.setId(id);
		return familyService.update(family);
	}

	@PutMapping("/{id}/availability")
	public Family updateAvailability(@PathVariable Long id, @RequestBody Family family) {
		family.setId(id);
		return familyService.update(family);
	}

	@GetMapping("/{id}/trust-score")
	public List<TrustScore> getTrustScore(@PathVariable Long id) {
		return trustScoreService.findByFamilyId(id);
	}

	@GetMapping("/{id}/trust-score/network")
	public List<TrustScore> getTrustScoreNetwork(@PathVariable Long id) {
		return trustScoreService.findByFamilyId(id);
	}

	@GetMapping("/{id}/interactions")
	public List<Interaction> getInteractions(@PathVariable Long id) {
		Map<Long, Interaction> interactions = new LinkedHashMap<>();
		for (Interaction interaction : interactionService.findByFamilyAId(id)) {
			interactions.put(interaction.getId(), interaction);
		}
		for (Interaction interaction : interactionService.findByFamilyBId(id)) {
			interactions.putIfAbsent(interaction.getId(), interaction);
		}
		return new ArrayList<>(interactions.values());
	}

	@GetMapping("/{id}/reciprocity")
	public List<Interaction> getReciprocity(@PathVariable Long id) {
		return getInteractions(id);
	}

	@DeleteMapping("/{id}/members/{memberId}")
	public void removeMember(@PathVariable Long id, @PathVariable Long memberId) {
		User user = userService.findById(memberId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + memberId));
		if (user.getFamilyId() == null || !user.getFamilyId().equals(id)) {
			throw new ResourceNotFoundException("Member not found in family: " + id);
		}
		user.setFamilyId(null);
		userService.save(user);
	}

}
