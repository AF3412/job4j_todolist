CREATE TABLE IF NOT EXISTS tasks
(
    id          SERIAL PRIMARY KEY,
    description TEXT,
    created     DATE,
    done        BOOLEAN
);

CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    email    TEXT,
    password TEXT
);

ALTER TABLE tasks ADD COLUMN user_id INT REFERENCES users(id);

INSERT INTO categories (name) VALUES ('INBOX'), ('WORK'), ('HOME'), ('SPORT');