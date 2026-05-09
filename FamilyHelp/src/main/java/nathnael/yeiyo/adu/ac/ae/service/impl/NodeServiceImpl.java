package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.Node;
import nathnael.yeiyo.adu.ac.ae.repository.NodeRepository;
import nathnael.yeiyo.adu.ac.ae.service.NodeService;

@Service
public class NodeServiceImpl implements NodeService {

  @Autowired
  private NodeRepository nodeRepository;

  @Override
  public Node save(Node node) {
    return nodeRepository.save(node);
  }

  @Override
  public Optional<Node> findById(Long id) {
    return nodeRepository.findById(id);
  }

  @Override
  public List<Node> findAll() {
    return nodeRepository.findAll();
  }

  @Override
  public Node update(Node node) {
    if (!nodeRepository.existsById(node.getId())) {
      throw new ResourceNotFoundException("Node not found with id: " + node.getId());
    }
    return nodeRepository.save(node);
  }

  @Override
  public void deleteById(Long id) {
    if (!nodeRepository.existsById(id)) {
      throw new ResourceNotFoundException("Node not found with id: " + id);
    }
    nodeRepository.deleteById(id);
  }

  @Override
  public List<Node> findByStatus(String status) {
    return nodeRepository.findByStatus(status);
  }

  @Override
  public Optional<Node> findByHostAndPort(String host, Integer port) {
    return nodeRepository.findByHostAndPort(host, port);
  }

}
