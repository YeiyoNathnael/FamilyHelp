package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.PostApplication;
import nathnael.yeiyo.adu.ac.ae.repository.PostApplicationRepository;
import nathnael.yeiyo.adu.ac.ae.service.PostApplicationService;

@Service
public class PostApplicationServiceImpl implements PostApplicationService {

  @Autowired
  private PostApplicationRepository postApplicationRepository;

  @Override
  public PostApplication save(PostApplication application) {
    return postApplicationRepository.save(application);
  }

  @Override
  public Optional<PostApplication> findById(Long id) {
    return postApplicationRepository.findById(id);
  }

  @Override
  public List<PostApplication> findAll() {
    return postApplicationRepository.findAll();
  }

  @Override
  public PostApplication update(PostApplication application) {
    if (!postApplicationRepository.existsById(application.getId())) {
      throw new ResourceNotFoundException("Application not found with id: " + application.getId());
    }
    return postApplicationRepository.save(application);
  }

  @Override
  public void deleteById(Long id) {
    if (!postApplicationRepository.existsById(id)) {
      throw new ResourceNotFoundException("Application not found with id: " + id);
    }
    postApplicationRepository.deleteById(id);
  }

  @Override
  public List<PostApplication> findByPostId(Long postId) {
    return postApplicationRepository.findByPostId(postId);
  }

  @Override
  public List<PostApplication> findByStatus(String status) {
    return postApplicationRepository.findByStatus(status);
  }

  @Override
  public List<PostApplication> findByApplicantFamilyId(Long familyId) {
    return postApplicationRepository.findByApplicantFamilyId(familyId);
  }

}
