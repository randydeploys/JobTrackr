package com.jobtrackr.backend.entity;

import java.time.LocalDateTime;

import com.jobtrackr.backend.model.InterviewType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "interview")
@Data
public class Interview {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @Enumerated(EnumType.STRING)
    private InterviewType type;
    private LocalDateTime interviewDate;
    private String interviewerName;
    private String location;
    private String feedback;

    public Interview() {
    
    }
}
