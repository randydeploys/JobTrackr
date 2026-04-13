package com.jobtrackr.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobtrackr.backend.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    
}
