package nathnael.yeiyo.adu.ac.ae.service;

import java.util.List;
import java.util.Optional;

import nathnael.yeiyo.adu.ac.ae.model.Node;

public interface NodeService {

  Node save(Node node);

  Optional<Node> findById(Long id);

  List<Node> findAll();

  Node update(Node node);

  void deleteById(Long id);

  List<Node> findByStatus(String status);
  
  Optional<Node> findByHostAndPort(String host, Integer port);
}
