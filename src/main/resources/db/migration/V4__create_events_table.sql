
CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    date VARCHAR(255),
    duration INT,
    description TEXT,
    place VARCHAR(255),
    time VARCHAR(255),
    ticket_price DOUBLE,
    capacity INT,
    image MEDIUMBLOB,
    organisation_id INT,
    event_type_id INT,
    event_status VARCHAR(255),
    CONSTRAINT fk_organisation
        FOREIGN KEY (organisation_id)
        REFERENCES organisation(id),
    CONSTRAINT fk_event_type
        FOREIGN KEY (event_type_id)
        REFERENCES event_types(id)
);


DO $$ BEGIN
    CREATE TYPE event_status AS ENUM ('AVAILABLE', 'FULL', 'FINISHED');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;


ALTER TABLE events
    ALTER COLUMN event_status TYPE event_status USING event_status::event_status;


CREATE TABLE events_users (
    event_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (event_id, user_id),
    CONSTRAINT fk_event
        FOREIGN KEY (event_id)
        REFERENCES events(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);
