package nathnael.yeiyo.adu.ac.ae.service;

import java.util.List;
import java.util.Optional;

import nathnael.yeiyo.adu.ac.ae.model.PostApplication;

public interface PostApplicationService {

  PostApplication save(PostApplication application);

  Optional<PostApplication> findById(Long id);

  List<PostApplication> findAll();

  PostApplication update(PostApplication application);

  void deleteById(Long id);

  List<PostApplication> findByPostId(Long postId);

  List<PostApplication> findByStatus(String status);
  
  List<PostApplication> findByApplicantFamilyId(Long familyId);
}
