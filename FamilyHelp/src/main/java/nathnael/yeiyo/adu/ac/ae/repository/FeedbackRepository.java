package nathnael.yeiyo.adu.ac.ae.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nathnael.yeiyo.adu.ac.ae.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
  List<Feedback> findByTaskId(Long taskId);
  List<Feedback> findByReviewedFamilyId(Long familyId);
  List<Feedback> findByReviewerFamilyId(Long familyId);
}
