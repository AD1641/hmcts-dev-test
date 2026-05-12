package uk.gov.hmcts.reform.dev.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import uk.gov.hmcts.reform.dev.models.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class CreateTaskRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Status is required")
    private TaskStatus status;

    @NotNull(message = "FutureOrPresent required")
    @FutureOrPresent(message = "Due date must be in the future or present")
    private LocalDate dueDate;
}
