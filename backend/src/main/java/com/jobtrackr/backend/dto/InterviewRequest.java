package com.jobtrackr.backend.dto;

import com.jobtrackr.backend.model.InterviewType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InterviewRequest {

    @NotNull(message = "Application ID is required")
    private Long applicationId;

    @NotNull(message = "Interview type is required")
    private InterviewType type;

    @NotNull(message = "Interview date is required")
    @FutureOrPresent(message = "Interview date cannot be in the past")
    private LocalDateTime interviewDate;

    @Size(max = 255, message = "Interviewer name must not exceed 255 characters")
    private String interviewerName;

    @Size(max = 255, message = "Location must not exceed 255 characters")
    private String location;

    @Size(max = 2000, message = "Feedback must not exceed 2000 characters")
    private String feedback;

}