package nathnael.yeiyo.adu.ac.ae.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nathnael.yeiyo.adu.ac.ae.model.Node;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
  List<Node> findByStatus(String status);
  Optional<Node> findByHostAndPort(String host, Integer port);
}
