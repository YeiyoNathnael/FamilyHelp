package nathnael.yeiyo.adu.ac.ae.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nathnael.yeiyo.adu.ac.ae.model.TrustScore;
import nathnael.yeiyo.adu.ac.ae.service.TrustScoreService;

@RestController
@RequestMapping("/trust")
public class TrustController {

	@Autowired
	private TrustScoreService trustScoreService;

	@GetMapping("/family/{id}")
	public List<TrustScore> getTrustScore(@PathVariable Long id) {
		return trustScoreService.findByFamilyId(id);
	}

	@GetMapping("/family/{id}/network")
	public List<TrustScore> getTrustScoreNetwork(@PathVariable Long id) {
		return trustScoreService.findByFamilyId(id);
	}

}
