package com.jobtrackr.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobtrackr.backend.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    
}
