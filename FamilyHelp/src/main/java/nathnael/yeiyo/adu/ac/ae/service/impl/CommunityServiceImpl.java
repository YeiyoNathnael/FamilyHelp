package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.Community;
import nathnael.yeiyo.adu.ac.ae.repository.CommunityRepository;
import nathnael.yeiyo.adu.ac.ae.service.CommunityService;

@Service
public class CommunityServiceImpl implements CommunityService {

  @Autowired
  private CommunityRepository communityRepository;

  @Override
  public Community save(Community community) {
    return communityRepository.save(community);
  }

  @Override
  public Optional<Community> findById(Long id) {
    return communityRepository.findById(id);
  }

  @Override
  public List<Community> findAll() {
    return communityRepository.findAll();
  }

  @Override
  public Community update(Community community) {
    if (!communityRepository.existsById(community.getId())) {
      throw new ResourceNotFoundException("Community not found with id: " + community.getId());
    }
    return communityRepository.save(community);
  }

  @Override
  public void deleteById(Long id) {
    if (!communityRepository.existsById(id)) {
      throw new ResourceNotFoundException("Community not found with id: " + id);
    }
    communityRepository.deleteById(id);
  }

  @Override
  public List<Community> findByCity(String city) {
    return communityRepository.findByCity(city);
  }

}
