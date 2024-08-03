CREATE TABLE organisation (
id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL
);
ALTER TABLE organisation
ADD CONSTRAINT name_not_empty CHECK (name <> '');