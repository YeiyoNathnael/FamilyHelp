package nathnael.yeiyo.adu.ac.ae.service;

import java.util.List;
import java.util.Optional;

import nathnael.yeiyo.adu.ac.ae.model.TaskTransaction;

public interface TaskTransactionService {

  TaskTransaction save(TaskTransaction task);

  Optional<TaskTransaction> findById(Long id);

  List<TaskTransaction> findAll();

  TaskTransaction update(TaskTransaction task);

  void deleteById(Long id);

  Optional<TaskTransaction> findByApplicationId(Long applicationId);

  List<TaskTransaction> findByStatus(String status);
  
}
