package nathnael.yeiyo.adu.ac.ae.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.PostApplication;
import nathnael.yeiyo.adu.ac.ae.service.PostApplicationService;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

	@Autowired
	private PostApplicationService postApplicationService;

	@GetMapping("/{id}")
	public PostApplication getApplication(@PathVariable Long id) {
		return postApplicationService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
	}

	@GetMapping("/posts/{postId}")
	public List<PostApplication> getApplicationsByPost(@PathVariable Long postId) {
		return postApplicationService.findByPostId(postId);
	}

	@PostMapping("/posts/{postId}")
	public PostApplication createApplication(@PathVariable Long postId, @RequestBody PostApplication application) {
		application.setPostId(postId);
		return postApplicationService.save(application);
	}

	@PutMapping("/{id}/accept")
	public PostApplication acceptApplication(@PathVariable Long id) {
		PostApplication application = getApplication(id);
		application.setStatus("RAFT_COMMITTED");
		return postApplicationService.update(application);
	}

	@PutMapping("/{id}/reject")
	public PostApplication rejectApplication(@PathVariable Long id) {
		PostApplication application = getApplication(id);
		application.setStatus("REJECTED");
		return postApplicationService.update(application);
	}

	@DeleteMapping("/{id}")
	public void deleteApplication(@PathVariable Long id) {
		postApplicationService.deleteById(id);
	}

}
