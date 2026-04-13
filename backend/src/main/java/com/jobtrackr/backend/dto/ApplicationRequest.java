package com.jobtrackr.backend.dto;

import com.jobtrackr.backend.model.ApplicationStatus;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ApplicationRequest {

    @NotBlank(message = "Job title is required")
    @Size(max = 255, message = "Job title must not exceed 255 characters")
    private String jobTitle;

    @NotNull(message = "Company ID is required")
    private Long companyId;

    @PastOrPresent(message = "Applied date cannot be in the future")
    private LocalDate appliedDate;

    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    @Size(max = 2000, message = "Notes must not exceed 2000 characters")
    private String notes;
}