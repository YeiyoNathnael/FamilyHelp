package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.TaskTransaction;
import nathnael.yeiyo.adu.ac.ae.repository.TaskTransactionRepository;
import nathnael.yeiyo.adu.ac.ae.service.TaskTransactionService;

@Service
public class TaskTransactionServiceImpl implements TaskTransactionService {

  @Autowired
  private TaskTransactionRepository taskTransactionRepository;

  @Override
  public TaskTransaction save(TaskTransaction task) {
    return taskTransactionRepository.save(task);
  }

  @Override
  public Optional<TaskTransaction> findById(Long id) {
    return taskTransactionRepository.findById(id);
  }

  @Override
  public List<TaskTransaction> findAll() {
    return taskTransactionRepository.findAll();
  }

  @Override
  public TaskTransaction update(TaskTransaction task) {
    if (!taskTransactionRepository.existsById(task.getId())) {
      throw new ResourceNotFoundException("Task transaction not found with id: " + task.getId());
    }
    return taskTransactionRepository.save(task);
  }

  @Override
  public void deleteById(Long id) {
    if (!taskTransactionRepository.existsById(id)) {
      throw new ResourceNotFoundException("Task transaction not found with id: " + id);
    }
    taskTransactionRepository.deleteById(id);
  }

  @Override
  public Optional<TaskTransaction> findByApplicationId(Long applicationId) {
    return taskTransactionRepository.findByApplicationId(applicationId);
  }

  @Override
  public List<TaskTransaction> findByStatus(String status) {
    return taskTransactionRepository.findByStatus(status);
  }

}
