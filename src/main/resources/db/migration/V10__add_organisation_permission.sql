-- V2__add_organisation_permission.sql
ALTER TABLE organisation
ADD COLUMN organisation_permission VARCHAR(255);
