CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(128) UNIQUE NOT NULL,
    login_type SMALLINT DEFAULT 0 NOT NULL,
    login_value VARCHAR(256) NOT NULL,
    groups INTEGER DEFAULT 0 NOT NULL
);

CREATE TABLE IF NOT EXISTS groups (
    id SERIAL PRIMARY KEY,
    owner_id INTEGER NOT NULL,
    title VARCHAR(128) NOT NULL,
    description TEXT,
    FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS meetings (
    id SERIAL PRIMARY KEY,
    group_id INTEGER NOT NULL,
    host_id INTEGER NOT NULL,
    name VARCHAR(128) NOT NULL,
    datetime BIGINT NOT NULL,
    type SMALLINT DEFAULT 0 NOT NULL,
    location VARCHAR(256) NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups(id),
    FOREIGN KEY (host_id) REFERENCES users(id)
);

