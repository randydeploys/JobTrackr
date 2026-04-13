package com.jobtrackr.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobtrackr.backend.entity.Company;
import com.jobtrackr.backend.repository.CompanyRepository;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

  @Autowired
  private CompanyRepository companyRepository;

  @GetMapping
  public List<Company> getAllCompanies() {
      return companyRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {                                                                       
      return companyRepository.findById(id)                                                                                                    
          .map(ResponseEntity::ok)
          .orElse(ResponseEntity.notFound().build());
  }
  
  @PostMapping
  public ResponseEntity<Company> createCompany(@RequestBody Company company) {
      return ResponseEntity.status(201).body(companyRepository.save(company));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company company) {
      return companyRepository.findById(id)
          .map(existingCompany -> {
              existingCompany.setName(company.getName());
              existingCompany.setWebsite(company.getWebsite());
              existingCompany.setCity(company.getCity());
              return ResponseEntity.ok(companyRepository.save(existingCompany));
          })
          .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
      if (!companyRepository.existsById(id)) {
          return ResponseEntity.notFound().build();
      }
      companyRepository.deleteById(id);
      return ResponseEntity.ok().build();
  }
}
    

