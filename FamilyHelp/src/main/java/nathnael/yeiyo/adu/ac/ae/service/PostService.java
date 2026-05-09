package nathnael.yeiyo.adu.ac.ae.service;

import java.util.List;
import java.util.Optional;

import nathnael.yeiyo.adu.ac.ae.model.Post;

public interface PostService {

  Post save(Post post);

  Optional<Post> findById(Long id);

  List<Post> findAll();

  Post update(Post post);

  void deleteById(Long id);
  
  List<Post> findByFamilyId(Long familyId);

  List<Post> findByStatus(String status);

  List<Post> findByPostType(String postType);
}
