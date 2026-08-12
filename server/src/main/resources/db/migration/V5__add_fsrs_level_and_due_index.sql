ALTER TABLE character_fsrs_state
    ADD COLUMN level INTEGER NOT NULL DEFAULT 0;

CREATE INDEX idx_character_fsrs_state_user_due
    ON character_fsrs_state (user_id, next_review_due_at);
