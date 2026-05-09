package nathnael.yeiyo.adu.ac.ae.service;

import java.util.List;
import java.util.Optional;

import nathnael.yeiyo.adu.ac.ae.model.Feedback;

public interface FeedbackService {

  Feedback save(Feedback feedback);

  Optional<Feedback> findById(Long id);

  List<Feedback> findAll();

  Feedback update(Feedback feedback);

  void deleteById(Long id);

  List<Feedback> findByTaskId(Long taskId);

  List<Feedback> findByReviewedFamilyId(Long familyId);
  
  List<Feedback> findByReviewerFamilyId(Long familyId);
}
