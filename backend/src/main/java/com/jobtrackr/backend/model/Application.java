package com.jobtrackr.backend.model;

import java.time.LocalDate;

public record Application(
    Long id,                  // identifiant unique
    String jobTitle,          // intitulé du poste
    Company company,          // Un record peut contenir un autre record
    LocalDate appliedDate,    // date de candidature
    ApplicationStatus status, // statut 
    String notes              // notes libres
) {}