package com.jobtrackr.backend.entity;

import java.time.LocalDate;

import com.jobtrackr.backend.model.ApplicationStatus;

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
@Data
@Table(name = "application")
public class Application {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobTitle;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    
    private LocalDate appliedDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    private String notes;

    public Application() {
    
    }
}
