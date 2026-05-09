package nathnael.yeiyo.adu.ac.ae.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nathnael.yeiyo.adu.ac.ae.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findByFamilyId(Long familyId);
  List<Post> findByStatus(String status);
  List<Post> findByPostType(String postType);
}
