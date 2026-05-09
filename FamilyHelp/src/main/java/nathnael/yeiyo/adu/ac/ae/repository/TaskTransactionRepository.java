package nathnael.yeiyo.adu.ac.ae.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nathnael.yeiyo.adu.ac.ae.model.TaskTransaction;

@Repository
public interface TaskTransactionRepository extends JpaRepository<TaskTransaction, Long> {
  Optional<TaskTransaction> findByApplicationId(Long applicationId);
  List<TaskTransaction> findByStatus(String status);
}
