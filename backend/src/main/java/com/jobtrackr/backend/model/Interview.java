package com.jobtrackr.backend.model;

import java.time.LocalDateTime;

public record Interview(
    Long id,    
    Long applicationId,
    LocalDateTime interviewDate,
    String interviewerName,
    InterviewType type, 
    String location,
    String feedback
) {}
