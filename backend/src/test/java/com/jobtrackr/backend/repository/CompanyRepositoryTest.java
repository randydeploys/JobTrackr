package com.jobtrackr.backend.repository;

import com.jobtrackr.backend.AbstractIntegrationTest;
import com.jobtrackr.backend.entity.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void shouldSaveAndFindCompany() {
        Company company = new Company();
        company.setName("Anthropic");
        company.setCity("San Francisco");
        company.setWebsite("https://anthropic.com");

        Company saved = companyRepository.save(company);

        assertThat(saved.getId()).isNotNull();
        assertThat(companyRepository.findById(saved.getId()))
            .isPresent()
            .hasValueSatisfying(c -> {
                assertThat(c.getName()).isEqualTo("Anthropic");
                assertThat(c.getCity()).isEqualTo("San Francisco");
            });
    }
}