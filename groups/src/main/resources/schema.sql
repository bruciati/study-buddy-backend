--
-- Table structure for table `groups`
--
CREATE TABLE IF NOT EXISTS groups (
    id SERIAL PRIMARY KEY,
    title VARCHAR(128) UNIQUE NOT NULL,
    description TEXT DEFAULT NULL
);

--
-- Table structure for table `members`
--
CREATE TABLE IF NOT EXISTS members (
    group_id SERIAL NOT NULL,
    user_id SERIAL NOT NULL,
    is_owner BOOLEAN DEFAULT false NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    PRIMARY KEY (user_id, group_id)
);
