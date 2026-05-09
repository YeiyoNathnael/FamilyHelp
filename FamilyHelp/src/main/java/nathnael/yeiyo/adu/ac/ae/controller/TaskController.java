package nathnael.yeiyo.adu.ac.ae.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.PostApplication;
import nathnael.yeiyo.adu.ac.ae.model.TaskTransaction;
import nathnael.yeiyo.adu.ac.ae.service.PostApplicationService;
import nathnael.yeiyo.adu.ac.ae.service.TaskTransactionService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskTransactionService taskTransactionService;

	@Autowired
	private PostApplicationService postApplicationService;

	@GetMapping
	public List<TaskTransaction> getAllTasks() {
		return taskTransactionService.findAll();
	}

	@GetMapping("/{id}")
	public TaskTransaction getTask(@PathVariable Long id) {
		return taskTransactionService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
	}

	@GetMapping("/families/{id}/tasks")
	public List<TaskTransaction> getTasksByFamily(@PathVariable Long id) {
		List<PostApplication> applications = postApplicationService.findByApplicantFamilyId(id);
		Set<Long> applicationIds = applications.stream().map(PostApplication::getId).collect(Collectors.toSet());
		List<TaskTransaction> tasks = new ArrayList<>();
		for (TaskTransaction task : taskTransactionService.findAll()) {
			if (applicationIds.contains(task.getApplicationId())) {
				tasks.add(task);
			}
		}
		return tasks;
	}

	@PutMapping("/{id}/complete")
	public TaskTransaction completeTask(@PathVariable Long id) {
		TaskTransaction task = getTask(id);
		task.setStatus("COMPLETED");
		task.setCompletedAt(LocalDateTime.now().toString());
		return taskTransactionService.update(task);
	}

	@PutMapping("/{id}/cancel")
	public TaskTransaction cancelTask(@PathVariable Long id) {
		TaskTransaction task = getTask(id);
		task.setStatus("CANCELLED");
		return taskTransactionService.update(task);
	}

}
