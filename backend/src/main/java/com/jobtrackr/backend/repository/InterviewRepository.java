package com.jobtrackr.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobtrackr.backend.entity.Interview;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    
}
