CREATE TABLE company (
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    website VARCHAR(255),
    city    VARCHAR(255)
);

CREATE TABLE application (
    id           BIGSERIAL PRIMARY KEY,
    job_title    VARCHAR(255) NOT NULL,
    company_id   BIGINT NOT NULL REFERENCES company(id) ON DELETE RESTRICT,
    applied_date DATE,
    status       VARCHAR(50) NOT NULL
                 CHECK (status IN ('APPLIED','PHONE_SCREEN','INTERVIEW','OFFER','REJECTED','ACCEPTED')),
    notes        TEXT
);

CREATE TABLE interview (
    id               BIGSERIAL PRIMARY KEY,
    application_id   BIGINT NOT NULL REFERENCES application(id) ON DELETE CASCADE,
    interview_date   TIMESTAMP,
    interviewer_name VARCHAR(255),
    type             VARCHAR(50)
                     CHECK (type IN ('PHONE','TECHNICAL','HR','FINAL')),
    location         VARCHAR(255),
    feedback         TEXT
);

-- Index sur les clés étrangères pour les requêtes par relation
CREATE INDEX idx_application_company_id ON application(company_id);
CREATE INDEX idx_interview_application_id ON interview(application_id);
