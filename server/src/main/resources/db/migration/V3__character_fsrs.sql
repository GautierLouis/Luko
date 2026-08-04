CREATE TABLE character_fsrs_state (
    id                  SERIAL PRIMARY KEY,
    user_id             INTEGER NOT NULL,
    character_code      INTEGER NOT NULL,
    difficulty          DOUBLE PRECISION NOT NULL,
    stability           DOUBLE PRECISION NOT NULL,
    last_reviewed_at    BIGINT NOT NULL,
    next_review_due_at  BIGINT NOT NULL,
    updated_at          BIGINT NOT NULL,
    CONSTRAINT fk_character_fsrs_state_user
        FOREIGN KEY (user_id) REFERENCES "user" (id)
        ON DELETE CASCADE,
    CONSTRAINT uq_character_fsrs_state_user_character
        UNIQUE (user_id, character_code)
);

CREATE INDEX idx_character_fsrs_state_next_review_due
    ON character_fsrs_state (user_id, next_review_due_at);
