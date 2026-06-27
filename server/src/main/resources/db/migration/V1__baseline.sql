CREATE TABLE IF NOT EXISTS hanzi (
    id SERIAL PRIMARY KEY,
    code INT NOT NULL,
    traditional VARCHAR(8) NULL,
    pinyin VARCHAR(64) NULL,
    pinyin_style2 VARCHAR(64) NULL,
    zhuyin_bopomofo VARCHAR(64) NULL,
    jyupting VARCHAR(32) NULL,
    decomposition1 VARCHAR(64) NULL,
    decomposition2_with_radical VARCHAR(64) NULL,
    meaning_decomposition2_with_radical TEXT NULL,
    decomposition3_graphical VARCHAR(64) NULL,
    component_in TEXT NULL,
    example_words TEXT NULL,
    meaning_junda TEXT NULL,
    key_word_rsh VARCHAR(64) NULL,
    hsk30_level VARCHAR(16) NULL,
    rank_rsh INT NULL,
    frequency_junda INT NULL,
    index_gscc VARCHAR(16) NULL,
    learning_order_ccm INT NULL,
    cc_cedict_definitions TEXT NULL
);

CREATE TABLE IF NOT EXISTS hsk_entry (
    id SERIAL PRIMARY KEY,
    simplified VARCHAR(32) NOT NULL,
    radical VARCHAR(8) NULL,
    frequency INT NULL,
    levels TEXT NOT NULL,
    pos TEXT NULL
);

CREATE TABLE IF NOT EXISTS hsk_form (
    id SERIAL PRIMARY KEY,
    entry_id INT NOT NULL,
    traditional VARCHAR(32) NULL,
    pinyin VARCHAR(64) NULL,
    "numeric" VARCHAR(64) NULL,
    wadegiles VARCHAR(64) NULL,
    bopomofo VARCHAR(64) NULL,
    romatzyh VARCHAR(64) NULL,
    meanings TEXT NULL,
    classifiers VARCHAR(64) NULL,
    CONSTRAINT fk_hsk_form_entry_id__id FOREIGN KEY (entry_id) REFERENCES hsk_entry(id) ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE IF NOT EXISTS hsk_entry_character (
    id SERIAL PRIMARY KEY,
    entry_id INT NOT NULL,
    code_point INT NOT NULL,
    CONSTRAINT fk_hsk_entry_character_entry_id__id FOREIGN KEY (entry_id) REFERENCES hsk_entry(id) ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE IF NOT EXISTS hsk_entry_level (
    id SERIAL PRIMARY KEY,
    entry_id INT NOT NULL,
    "level" VARCHAR(16) NOT NULL,
    CONSTRAINT fk_hsk_entry_level_entry_id__id FOREIGN KEY (entry_id) REFERENCES hsk_entry(id) ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE IF NOT EXISTS "dictionary" (
    id SERIAL PRIMARY KEY,
    code INT NOT NULL,
    "char" VARCHAR(4) NOT NULL,
    decomposition TEXT NULL,
    medians TEXT NOT NULL,
    "level" INT NOT NULL,
    hsk_level INT NOT NULL
);

CREATE TABLE IF NOT EXISTS "character" (
    id SERIAL PRIMARY KEY,
    code INT NOT NULL,
    definition TEXT NULL,
    pinyin TEXT NULL,
    decomposition VARCHAR(110) NOT NULL,
    etymology_type VARCHAR(50) NULL,
    etymology_phonetic TEXT NULL,
    etymology_semantic TEXT NULL,
    etymology_hint TEXT NULL,
    radical VARCHAR(4) NULL,
    matches TEXT NULL
);

CREATE TABLE IF NOT EXISTS graphic (
    id SERIAL PRIMARY KEY,
    code INT NOT NULL,
    strokes TEXT NOT NULL,
    medians TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL PRIMARY KEY,
    firebase_uid VARCHAR(128) NOT NULL,
    fcm_token VARCHAR(255) NULL,
    platform VARCHAR(64) NOT NULL,
    is_anonymous BOOLEAN DEFAULT TRUE NOT NULL,
    created_at BIGINT NOT NULL,
    updated_at BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS session_seed (
    id SERIAL PRIMARY KEY,
    seed BIGINT NOT NULL,
    levels VARCHAR(20) NOT NULL,
    "limit" INT NOT NULL
);
