package com.jobtrackr.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import com.jobtrackr.backend.dto.ApplicationRequest;
import com.jobtrackr.backend.entity.Application;
import com.jobtrackr.backend.entity.Company;
import com.jobtrackr.backend.repository.ApplicationRepository;
import com.jobtrackr.backend.repository.CompanyRepository;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationRepository applicationRepository;
    private final CompanyRepository companyRepository;

    public ApplicationController(ApplicationRepository applicationRepository,
                                  CompanyRepository companyRepository) {
        this.applicationRepository = applicationRepository;
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        return applicationRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(@Valid @RequestBody ApplicationRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));

        Application application = new Application();
        application.setJobTitle(request.getJobTitle());
        application.setCompany(company);
        application.setAppliedDate(request.getAppliedDate());
        application.setStatus(request.getStatus());
        application.setNotes(request.getNotes());

        return ResponseEntity.status(HttpStatus.CREATED).body(applicationRepository.save(application));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id,
                                                          @Valid @RequestBody ApplicationRequest request) {
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));

        Company company = companyRepository.findById(request.getCompanyId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));

        application.setJobTitle(request.getJobTitle());
        application.setCompany(company);
        application.setAppliedDate(request.getAppliedDate());
        application.setStatus(request.getStatus());
        application.setNotes(request.getNotes());

        return ResponseEntity.ok(applicationRepository.save(application));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        if (!applicationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        applicationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}