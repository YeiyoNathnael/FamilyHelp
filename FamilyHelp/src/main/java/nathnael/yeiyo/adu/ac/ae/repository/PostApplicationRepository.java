package nathnael.yeiyo.adu.ac.ae.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nathnael.yeiyo.adu.ac.ae.model.PostApplication;

@Repository
public interface PostApplicationRepository extends JpaRepository<PostApplication, Long> {
  List<PostApplication> findByPostId(Long postId);
  List<PostApplication> findByStatus(String status);
  List<PostApplication> findByApplicantFamilyId(Long familyId);
}
