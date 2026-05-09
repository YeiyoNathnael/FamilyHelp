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
@RequestMapping("/families")
public class TrustController {

	@Autowired
	private TrustScoreService trustScoreService;

	@GetMapping("/{id}/trust-score")
	public List<TrustScore> getTrustScore(@PathVariable Long id) {
		return trustScoreService.findByFamilyId(id);
	}

	@GetMapping("/{id}/trust-score/network")
	public List<TrustScore> getTrustScoreNetwork(@PathVariable Long id) {
		return trustScoreService.findByFamilyId(id);
	}

}
