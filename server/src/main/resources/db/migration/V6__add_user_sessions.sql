CREATE TABLE session (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    date VARCHAR(64) NOT NULL,
    "offset" VARCHAR(16) NOT NULL,
    duration BIGINT NOT NULL,
    difficulty VARCHAR(32) NOT NULL,
    questions_count INT NOT NULL,
    accuracy DOUBLE PRECISION NOT NULL
);

CREATE INDEX idx_session_user_id ON session (user_id);

CREATE TABLE session_response (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES session(id) ON DELETE CASCADE,
    code INT NOT NULL,
    overall_accuracy REAL NOT NULL,
    response VARCHAR(4096) NOT NULL
);

CREATE INDEX idx_session_response_session_id ON session_response (session_id);
CREATE INDEX idx_session_response_code ON session_response (code);
