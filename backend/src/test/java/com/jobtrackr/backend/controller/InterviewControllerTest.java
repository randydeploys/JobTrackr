package com.jobtrackr.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jobtrackr.backend.AbstractIntegrationTest;
import com.jobtrackr.backend.entity.Application;
import com.jobtrackr.backend.entity.Company;
import com.jobtrackr.backend.model.ApplicationStatus;
import com.jobtrackr.backend.model.InterviewType;
import com.jobtrackr.backend.repository.ApplicationRepository;
import com.jobtrackr.backend.repository.CompanyRepository;
import com.jobtrackr.backend.repository.InterviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class InterviewControllerTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private Long applicationId;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        interviewRepository.deleteAll();
        applicationRepository.deleteAll();
        companyRepository.deleteAll();

        Company company = new Company();
        company.setName("TestCorp");
        Company savedCompany = companyRepository.save(company);

        Application application = new Application();
        application.setJobTitle("Développeur Java");
        application.setCompany(savedCompany);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setAppliedDate(LocalDate.now());
        applicationId = applicationRepository.save(application).getId();
    }

    @Test
    void getAllInterviews_returnsOk() throws Exception {
        mockMvc.perform(get("/api/interviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void createInterview_returnsCreated() throws Exception {
        Map<String, Object> body = Map.of(
                "applicationId", applicationId,
                "type", InterviewType.TECHNICAL.name(),
                "interviewDate", LocalDateTime.now().plusDays(1).toString()
        );

        mockMvc.perform(post("/api/interviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("TECHNICAL"));
    }

    @Test
    void createInterview_missingApplicationId_returns400() throws Exception {
        Map<String, Object> body = Map.of(
                "type", InterviewType.PHONE.name(),
                "interviewDate", LocalDateTime.now().plusDays(1).toString()
        );

        mockMvc.perform(post("/api/interviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.applicationId").exists());
    }

    @Test
    void getInterviewById_notFound_returns404() throws Exception {
        mockMvc.perform(get("/api/interviews/99999"))
                .andExpect(status().isNotFound());
    }
}
