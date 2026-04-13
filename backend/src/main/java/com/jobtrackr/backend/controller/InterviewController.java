package com.jobtrackr.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import com.jobtrackr.backend.dto.InterviewRequest;
import com.jobtrackr.backend.entity.Application;
import com.jobtrackr.backend.entity.Interview;
import com.jobtrackr.backend.repository.ApplicationRepository;
import com.jobtrackr.backend.repository.InterviewRepository;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;

    public InterviewController(InterviewRepository interviewRepository,
                                ApplicationRepository applicationRepository) {
        this.interviewRepository = interviewRepository;
        this.applicationRepository = applicationRepository;
    }

    @GetMapping
    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interview> getInterviewById(@PathVariable Long id) {
        return interviewRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Interview> createInterview(@Valid @RequestBody InterviewRequest request) {
        Application application = applicationRepository.findById(request.getApplicationId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));

        Interview interview = new Interview();
        interview.setApplication(application);
        interview.setType(request.getType());
        interview.setInterviewDate(request.getInterviewDate());
        interview.setInterviewerName(request.getInterviewerName());
        interview.setLocation(request.getLocation());
        interview.setFeedback(request.getFeedback());

        return ResponseEntity.status(HttpStatus.CREATED).body(interviewRepository.save(interview));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Interview> updateInterview(@PathVariable Long id,
                                                      @Valid @RequestBody InterviewRequest request) {
        Interview interview = interviewRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Interview not found"));

        Application application = applicationRepository.findById(request.getApplicationId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));

        interview.setApplication(application);
        interview.setType(request.getType());
        interview.setInterviewDate(request.getInterviewDate());
        interview.setInterviewerName(request.getInterviewerName());
        interview.setLocation(request.getLocation());
        interview.setFeedback(request.getFeedback());

        return ResponseEntity.ok(interviewRepository.save(interview));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {
        if (!interviewRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        interviewRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}