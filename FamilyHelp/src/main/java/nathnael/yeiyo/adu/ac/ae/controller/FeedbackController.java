package nathnael.yeiyo.adu.ac.ae.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nathnael.yeiyo.adu.ac.ae.model.Feedback;
import nathnael.yeiyo.adu.ac.ae.service.FeedbackService;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@GetMapping("/families/{id}")
	public List<Feedback> getFamilyFeedback(@PathVariable Long id) {
		return feedbackService.findByReviewedFamilyId(id);
	}

	@GetMapping("/tasks/{id}")
	public List<Feedback> getTaskFeedback(@PathVariable Long id) {
		return feedbackService.findByTaskId(id);
	}

	@PostMapping("/tasks/{id}")
	public Feedback createFeedback(@PathVariable Long id, @RequestBody Feedback feedback) {
		feedback.setTaskId(id);
		return feedbackService.save(feedback);
	}

}
