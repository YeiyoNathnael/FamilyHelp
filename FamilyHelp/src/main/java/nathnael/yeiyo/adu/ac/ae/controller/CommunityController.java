package nathnael.yeiyo.adu.ac.ae.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.Community;
import nathnael.yeiyo.adu.ac.ae.model.Family;
import nathnael.yeiyo.adu.ac.ae.service.CommunityService;
import nathnael.yeiyo.adu.ac.ae.service.FamilyService;

@RestController
@RequestMapping("/communities")
public class CommunityController {

	@Autowired
	private CommunityService communityService;

	@Autowired
	private FamilyService familyService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Community createCommunity(@RequestBody Community community) {
		return communityService.create(community);
	}

	@GetMapping("/{id}")
	public Community getCommunityById(@PathVariable Long id) {
		return communityService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Community not found with id: " + id));
	}

	@GetMapping("/{id}/families")
	public List<Family> getFamiliesByCommunity(@PathVariable Long id) {
		return familyService.findByCommunityId(id);
	}

	@GetMapping("/{id}/families/active")
	public List<Family> getActiveFamiliesByCommunity(@PathVariable Long id) {
		List<Family> activeFamilies = new ArrayList<>();
		for (Family family : familyService.findByCommunityId(id)) {
			if (family.getLastActive() != null) {
				activeFamilies.add(family);
			}
		}
		return activeFamilies;
	}

	@GetMapping("/{id}/skill-map")
	public List<Family> getSkillMap(@PathVariable Long id) {
		return familyService.findByCommunityId(id);
	}

}
