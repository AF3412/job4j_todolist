CREATE TABLE IF NOT EXISTS tasks
(
    id      SERIAL PRIMARY KEY,
    description    TEXT,
    created DATE,
    done    BOOLEAN
);