package uk.gov.hmcts.reform.dev.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.dto.CreateTaskRequest;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskStatus;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task create(CreateTaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());
        return repository.save(task);
    }

    public List<Task> getAll() {
        return repository.findAll();
    }

    public Task getById(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    public Task updateStatus(UUID id, TaskStatus status) {
        Task task = getById(id);
        task.setStatus(status);
        return repository.save(task);
    }

    public void delete(UUID id) {
        Task task = getById(id);
        repository.delete(task);
    }
}
