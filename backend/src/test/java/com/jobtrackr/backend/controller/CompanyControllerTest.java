package com.jobtrackr.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobtrackr.backend.AbstractIntegrationTest;
import com.jobtrackr.backend.entity.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CompanyControllerTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getAllCompanies_returnsOk() throws Exception {
        mockMvc.perform(get("/api/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void createCompany_returnsCreated() throws Exception {
        Company company = new Company();
        company.setName("Anthropic");
        company.setWebsite("https://anthropic.com");
        company.setCity("San Francisco");

        mockMvc.perform(post("/api/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Anthropic"));
    }

    @Test
    void getCompanyById_notFound_returns404() throws Exception {
        mockMvc.perform(get("/api/companies/99999"))
                .andExpect(status().isNotFound());
    }
}
