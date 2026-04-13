package com.jobtrackr.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jobtrackr.backend.AbstractIntegrationTest;
import com.jobtrackr.backend.entity.Company;
import com.jobtrackr.backend.model.ApplicationStatus;
import com.jobtrackr.backend.repository.ApplicationRepository;
import com.jobtrackr.backend.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ApplicationControllerTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private Long companyId;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        applicationRepository.deleteAll();
        companyRepository.deleteAll();

        Company company = new Company();
        company.setName("TestCorp");
        company.setCity("Paris");
        companyId = companyRepository.save(company).getId();
    }

    @Test
    void getAllApplications_returnsOk() throws Exception {
        mockMvc.perform(get("/api/applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void createApplication_returnsCreated() throws Exception {
        Map<String, Object> body = Map.of(
                "jobTitle", "Développeur Java",
                "companyId", companyId,
                "appliedDate", LocalDate.now().toString(),
                "status", ApplicationStatus.APPLIED.name()
        );

        mockMvc.perform(post("/api/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.jobTitle").value("Développeur Java"));
    }

    @Test
    void createApplication_missingJobTitle_returns400() throws Exception {
        Map<String, Object> body = Map.of(
                "companyId", companyId,
                "status", ApplicationStatus.APPLIED.name()
        );

        mockMvc.perform(post("/api/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.jobTitle").exists());
    }

    @Test
    void getApplicationById_notFound_returns404() throws Exception {
        mockMvc.perform(get("/api/applications/99999"))
                .andExpect(status().isNotFound());
    }
}
