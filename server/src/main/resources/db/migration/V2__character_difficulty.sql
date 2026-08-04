CREATE TABLE IF NOT EXISTS character_complexity (
    id                  SERIAL PRIMARY KEY,
    code                INTEGER NOT NULL UNIQUE,
    stroke_count        INTEGER NOT NULL,
    path_length         DOUBLE PRECISION NOT NULL,
    component_count     INTEGER NOT NULL,
    complexity_factor   DOUBLE PRECISION NOT NULL,
    computed_at         BIGINT NOT NULL,
    CONSTRAINT fk_character_complexity_code
        FOREIGN KEY (code) REFERENCES dictionary (code)
        ON DELETE CASCADE
);

CREATE INDEX idx_character_complexity_code ON character_complexity (code);
