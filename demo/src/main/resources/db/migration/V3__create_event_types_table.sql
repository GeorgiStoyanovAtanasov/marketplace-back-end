CREATE TABLE event_types (
    id SERIAL PRIMARY KEY,
    type_name VARCHAR(255) NOT NULL
);

-- Constraint for non-empty type_name
ALTER TABLE event_types
    ADD CONSTRAINT type_name_not_empty CHECK (type_name <> '');