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
import nathnael.yeiyo.adu.ac.ae.model.Post;
import nathnael.yeiyo.adu.ac.ae.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping
	public List<Post> getAllPosts() {
		return postService.findAll();
	}

	@GetMapping("/{id}")
	public Post getPostById(@PathVariable Long id) {
		return postService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
	}

	@GetMapping("/urgent")
	public List<Post> getUrgentPosts() {
		return postService.findAll().stream()
				.filter(post -> Boolean.TRUE.equals(post.getUrgency()))
				.toList();
	}

	@GetMapping("/families/{id}/posts")
	public List<Post> getPostsByFamily(@PathVariable Long id) {
		return postService.findByFamilyId(id);
	}

	@PostMapping
	public Post createPost(@RequestBody Post post) {
		return postService.save(post);
	}

	@PutMapping("/{id}")
	public Post updatePost(@PathVariable Long id, @RequestBody Post post) {
		post.setId(id);
		return postService.update(post);
	}

	@DeleteMapping("/{id}")
	public void deletePost(@PathVariable Long id) {
		postService.deleteById(id);
	}

}
