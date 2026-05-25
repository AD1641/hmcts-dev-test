package uk.gov.hmcts.reform.dev.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.dev.dto.CreateTaskRequest;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskStatus;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService service;

    @Test
    void shouldCreateTask() {

        CreateTaskRequest request = new CreateTaskRequest();

        request.setTitle("Test Task");
        request.setStatus(TaskStatus.PENDING);
        request.setDueDate(LocalDate.now().plusDays(1));

        Task savedTask = new Task();
        savedTask.setTitle("Test Task");

        when(repository.save(any(Task.class)))
            .thenReturn(savedTask);

        Task result = service.create(request);

        assertEquals("Test Task", result.getTitle());
    }

    @Test
    void shouldGetTaskById() {

        UUID id = UUID.randomUUID();

        Task task = new Task();
        task.setId(id);

        when(repository.findById(id))
            .thenReturn(Optional.of(task));

        Task result = service.getById(id);

        assertEquals(id, result.getId());
    }

    @Test
    void shouldThrowWhenTaskNotFound() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
            .thenReturn(Optional.empty());

        assertThrows(
            EntityNotFoundException.class,
            () -> service.getById(id)
        );
    }
}
