CREATE TABLE IF NOT EXISTS ems.position (
    id SERIAL PRIMARY KEY,
    code TEXT NOT NULL ,
    name TEXT NOT NULL
);

ALTER TABLE ems.position
    ADD CONSTRAINT position_code_unique UNIQUE (code);
