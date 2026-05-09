package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.Post;
import nathnael.yeiyo.adu.ac.ae.repository.PostRepository;
import nathnael.yeiyo.adu.ac.ae.service.PostService;

@Service
public class PostServiceImpl implements PostService {

  @Autowired
  private PostRepository postRepository;

  @Override
  public Post save(Post post) {
    return postRepository.save(post);
  }

  @Override
  public Optional<Post> findById(Long id) {
    return postRepository.findById(id);
  }

  @Override
  public List<Post> findAll() {
    return postRepository.findAll();
  }

  @Override
  public Post update(Post post) {
    if (!postRepository.existsById(post.getId())) {
      throw new ResourceNotFoundException("Post not found with id: " + post.getId());
    }
    return postRepository.save(post);
  }

  @Override
  public void deleteById(Long id) {
    if (!postRepository.existsById(id)) {
      throw new ResourceNotFoundException("Post not found with id: " + id);
    }
    postRepository.deleteById(id);
  }

  @Override
  public List<Post> findByFamilyId(Long familyId) {
    return postRepository.findByFamilyId(familyId);
  }

  @Override
  public List<Post> findByStatus(String status) {
    return postRepository.findByStatus(status);
  }

  @Override
  public List<Post> findByPostType(String postType) {
    return postRepository.findByPostType(postType);
  }

}
