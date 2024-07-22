-- V5__create_managers_table.sql

CREATE TABLE managers (
    id SERIAL PRIMARY KEY,
    organisation_id INT,
    user_id INT,
    CONSTRAINT fk_organisation
        FOREIGN KEY (organisation_id)
        REFERENCES organisation(id),
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);
