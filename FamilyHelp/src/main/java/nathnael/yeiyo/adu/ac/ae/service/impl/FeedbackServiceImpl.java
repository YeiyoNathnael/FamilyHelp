package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.Feedback;
import nathnael.yeiyo.adu.ac.ae.repository.FeedbackRepository;
import nathnael.yeiyo.adu.ac.ae.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

  @Autowired
  private FeedbackRepository feedbackRepository;

  @Override
  public Feedback save(Feedback feedback) {
    return feedbackRepository.save(feedback);
  }

  @Override
  public Optional<Feedback> findById(Long id) {
    return feedbackRepository.findById(id);
  }

  @Override
  public List<Feedback> findAll() {
    return feedbackRepository.findAll();
  }

  @Override
  public Feedback update(Feedback feedback) {
    if (!feedbackRepository.existsById(feedback.getId())) {
      throw new ResourceNotFoundException("Feedback not found with id: " + feedback.getId());
    }
    return feedbackRepository.save(feedback);
  }

  @Override
  public void deleteById(Long id) {
    if (!feedbackRepository.existsById(id)) {
      throw new ResourceNotFoundException("Feedback not found with id: " + id);
    }
    feedbackRepository.deleteById(id);
  }

  @Override
  public List<Feedback> findByTaskId(Long taskId) {
    return feedbackRepository.findByTaskId(taskId);
  }

  @Override
  public List<Feedback> findByReviewedFamilyId(Long familyId) {
    return feedbackRepository.findByReviewedFamilyId(familyId);
  }

  @Override
  public List<Feedback> findByReviewerFamilyId(Long familyId) {
    return feedbackRepository.findByReviewerFamilyId(familyId);
  }

}
